package com.niuma.rainapithirdparty.listener;

import com.niuma.rainapicommon.constant.RedisConstant;
import com.niuma.rainapicommon.model.dto.SmsDTO;
import com.niuma.rainapithirdparty.utils.SendSmsUtils;
import com.rabbitmq.client.Channel;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.io.IOException;

import static com.niuma.rainapicommon.constant.RabbitMqConstant.SMS_DELAY_QUEUE_NAME;
import static com.niuma.rainapicommon.constant.RabbitMqConstant.SMS_QUEUE_NAME;

/**
 * @author niuma
 * @create 2023-04-29 15:35
 */
@Component
@Slf4j
public class SendSmsListener {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SendSmsUtils sendSmsUtils;

    /**
     * 监听发送短信普通队列
     * @param smsDTO
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = SMS_QUEUE_NAME)
    public void sendSmsListener(SmsDTO smsDTO, Message message, Channel channel) throws IOException {
        String messageId = message.getMessageProperties().getMessageId();

        int retryCount = (int) redisTemplate.opsForHash().get(RedisConstant.SMS_MESSAGE_PREFIX + messageId, "retryCount");
        if (retryCount > 3) {
            //重试次数大于3，直接放到死信队列
            log.error("短信消息重试超过3次：{}",  messageId);
            //basicReject方法拒绝deliveryTag对应的消息，第二个参数是否requeue，true则重新入队列，否则丢弃或者进入死信队列。
            //该方法reject后，该消费者还是会消费到该条被reject的消息。
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            redisTemplate.delete(RedisConstant.SMS_MESSAGE_PREFIX + messageId);
            return;
        }
        try {
            String phoneNum = smsDTO.getPhoneNum();
            String code = smsDTO.getCode();
            if(StringUtils.isAnyBlank(phoneNum,code)){
                throw new RuntimeException("sendSmsListener参数为空");
            }

            // 发送消息
            // todo 就是在这里利用腾讯的短信接口来发送的，并且接收发送的状态，如果发送失败，会重试，继续发送，最多三次，如果还失败，就将发送端中
            // todo 中设置的缓存中这个短信消息记录删掉
            SendSmsResponse sendSmsResponse = sendSmsUtils.sendSmsResponse(phoneNum, code);
            SendStatus[] sendStatusSet = sendSmsResponse.getSendStatusSet();
            SendStatus sendStatus = sendStatusSet[0];
            if(!"Ok".equals(sendStatus.getCode()) ||!"send success".equals(sendStatus.getMessage())){
                throw new RuntimeException("发送验证码失败");
            }

            // todo 手动确认消息，通知发送端我这边发送成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("短信发送成功：{}",smsDTO);
            redisTemplate.delete(RedisConstant.SMS_MESSAGE_PREFIX + messageId);
        } catch (Exception e) {
            redisTemplate.opsForHash().put(RedisConstant.SMS_MESSAGE_PREFIX+messageId,"retryCount",retryCount+1);
            //basicReject方法拒绝deliveryTag对应的消息，第二个参数是否requeue，true则重新入队列，否则丢弃或者进入死信队列。
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

    /**
     * 监听到发送短信死信队列
     * @param sms
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = SMS_DELAY_QUEUE_NAME)
    public void smsDelayQueueListener(SmsDTO sms, Message message, Channel channel) throws IOException {
        try{
            log.error("监听到死信队列消息==>{}",sms);
            //一定要确认消息，否则正常队列里的消息不会被删除
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
