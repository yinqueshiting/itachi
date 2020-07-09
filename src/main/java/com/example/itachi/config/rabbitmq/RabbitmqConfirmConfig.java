package com.example.itachi.config.rabbitmq;

import com.example.itachi.entity.CustomCorrelationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息达到Broker后触发回调，确认消息是否正确到达Exchange中
 */
@Slf4j
public class RabbitmqConfirmConfig implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        CustomCorrelationData customCorrelationData = (CustomCorrelationData)correlationData;
        log.info("customCorrelationData:{}",customCorrelationData);

        if (ack){
            log.info("消息投放成功");
        }else {
            log.info("投放失败");
            try{
                //此处可以根据customCorrelationData内的X,RoutingKey这些来进行重发
                log.info("此处来重发");
            }catch (Exception e){
                if(e instanceof AmqpConnectException){
                    //这种属于RabbitMQ Broker出现了问题，重发也没有意义，尝试其它方式做保存
                    log.info("RabbitMQ服务器出现问题了");
                }
            }
        }

    }
}
