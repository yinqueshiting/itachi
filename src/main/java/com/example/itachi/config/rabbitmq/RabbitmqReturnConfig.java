package com.example.itachi.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息从交换机发送到相应队列失败时触发（比如根据发送消息时指定的RoutingKey找不到队列时会触发）
 */
@Slf4j
public class RabbitmqReturnConfig implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("return机制message：{}",message);
        log.info("return机制replyCode：{}",replyCode);
        log.info("return机制exchange：{}",exchange);
        log.info("return机制replyText：{}",replyText);
        log.info("return机制routingKey：{}",routingKey);
    }
}
