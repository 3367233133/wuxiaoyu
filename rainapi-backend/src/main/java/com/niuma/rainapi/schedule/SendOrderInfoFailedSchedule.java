package com.niuma.rainapi.schedule;

import com.niuma.rainapi.utils.SmsRabbitMqUtils;
import com.niuma.rainapicommon.common.ErrorCode;
import com.niuma.rainapicommon.constant.RedisConstant;
import com.niuma.rainapicommon.exception.BusinessException;
import com.niuma.rainapicommon.model.dto.SmsDTO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.niuma.rainapicommon.constant.LockConstant.SMS_SEND_FAILED;

/**
 * 订单发送失败重试定时任务
 * @author niuma
 * @create 2023-05-04 16:29
 */
@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class SendOrderInfoFailedSchedule {
    @Resource
    RedissonClient redissonClient;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    SmsRabbitMqUtils rabbitMqUtils;

    @Scheduled(cron = "*/5 * * * * ?")
    public void sendOrderInfoFailed(){
        RLock lock = redissonClient.getLock(SMS_SEND_FAILED);
        try {
            boolean tryLock = lock.tryLock(6, 3, TimeUnit.SECONDS);
            if(tryLock){
                Set keys = redisTemplate.keys(RedisConstant.SMS_ORDER_NUM_PREFIX + "*");
                for (Object key : keys) {
                    //重新发送
                    SmsDTO smsDTO = (SmsDTO) redisTemplate.opsForValue().get(key);
                    redisTemplate.delete(key);
                    rabbitMqUtils.sendSmsAsync(smsDTO);
                }
            }
        } catch (InterruptedException e) {
            log.error("===定时任务:获取失败生产者发送消息redis出现bug===");
            throw new BusinessException(ErrorCode.OPERATION_ERROR,e.getMessage());
        }finally {
            lock.unlock();
        }

    }
}
