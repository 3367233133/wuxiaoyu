package com.niuma.rainapicommon.constant;

/**
 * @author niuma
 * @create 2023-04-28 21:30
 */
public interface RabbitMqConstant {
    /* 短信相关 */

    String SMS_QUEUE_NAME = "sms.queue";
    String SMS_EXCHANGE_NAME = "sms.exchange";
    String SMS_EXCHANGE_ROUTING_KEY = "sms.send";

    String SMS_DELAY_QUEUE_NAME = "sms.delay.queue";
    String SMS_DELAY_EXCHANGE_ROUTING_KEY = "sms.release";


    /* 订单相关 */

    String ORDER_EXCHANGE_NAME = "order.exchange";

    String ORDER_SEND_QUEUE_NAME = "order.send.queue";
    String ORDER_SEND_EXCHANGE_ROUTING_KEY = "order.send";

    String ORDER_SUCCESS_QUEUE_NAME = "order.pay.success.queue";
    String ORDER_SUCCESS_EXCHANGE_ROUTING_KEY = "order.pay.success";

    String ORDER_TIMEOUT_QUEUE_NAME = "order.timeout.queue";
    String ORDER_TIMEOUT_EXCHANGE_ROUTING_KEY = "order.timeout";
}
