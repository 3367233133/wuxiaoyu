package com.niuma.rainapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.niuma.rainapicommon.constant.RabbitMqConstant.*;

/**
 * RabbitMQ配置
 * @author niumazlb
 */
@Slf4j
@Configuration
public class SmsRabbitMqConfig {


    /**
     * 普通队列
     * @return
     */
    @Bean("SMS_QUEUE")
    public Queue smsQueue(){
        Map<String, Object> arguments = new HashMap<>();
        //声明死信队列和交换机消息，过期时间：1分钟
        arguments.put("x-dead-letter-exchange", SMS_EXCHANGE_NAME);
        arguments.put("x-dead-letter-routing-key", SMS_DELAY_EXCHANGE_ROUTING_KEY);
        arguments.put("x-message-ttl", 60000);
        return new Queue(SMS_QUEUE_NAME,true,false,false ,arguments);
    }

    /**
     * 死信队列：消息重试三次后放入死信队列
     * @return
     */
    @Bean("SMS_DEAD_LETTER")
    public Queue smsDeadLetter(){
        return new Queue(SMS_DELAY_QUEUE_NAME, true, false, false);
    }

    /**
     * 主题交换机
     * @return
     */
    @Bean("SMS_EXCHANGE")
    public Exchange smsExchange() {
        return new TopicExchange(SMS_EXCHANGE_NAME, true, false);
    }


    /**
     * 交换机和普通队列绑定
     * @return
     */
    @Bean
    public Binding smsBinding(@Qualifier("SMS_QUEUE") Queue smsQueue, @Qualifier("SMS_EXCHANGE") Exchange smsExchange){
        //return new Binding(SMS_QUEUE_NAME, Binding.DestinationType.QUEUE,SMS_EXCHANGE_NAME,SMS_EXCHANGE_ROUTING_KEY,null);
        return BindingBuilder.bind(smsQueue).to(smsExchange).with(SMS_EXCHANGE_ROUTING_KEY).noargs();
    }

    /**
     * 交换机和死信队列绑定
     * @return
     */
    @Bean
    public Binding smsDelayBinding(@Qualifier("SMS_DEAD_LETTER") Queue smsDeadQueue, @Qualifier("SMS_EXCHANGE") Exchange smsExchange){
//        return new Binding(SMS_DELAY_QUEUE_NAME, Binding.DestinationType.QUEUE,SMS_EXCHANGE_NAME,SMS_DELAY_EXCHANGE_ROUTING_KEY,null);
        return BindingBuilder.bind(smsDeadQueue).to(smsExchange).with(SMS_DELAY_EXCHANGE_ROUTING_KEY).noargs();
    }


}
