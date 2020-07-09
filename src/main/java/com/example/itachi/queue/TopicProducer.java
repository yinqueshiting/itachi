package com.example.itachi.queue;

import com.example.itachi.config.rabbitmq.TopicConfig;
import com.example.itachi.entity.CustomCorrelationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class TopicProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey,String json){
        Message message = MessageBuilder.withBody(json.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setMessageId(UUID.randomUUID()+"")
                //.setExpiration(8000+"")//设置这个消息的TTL为8s
                .build();
        //加上自定义的CorrelationData对象，在confirm失败时进行重发
        CustomCorrelationData customCorrelationData = new CustomCorrelationData();

        customCorrelationData.setId(String.valueOf(UUID.randomUUID()));
        customCorrelationData.setMessage(message);
        customCorrelationData.setRoutingKey(routingKey);
        customCorrelationData.setExchange(TopicConfig.TOPIC_EXCHANGE);

        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE,routingKey,message,customCorrelationData);
        log.info("topic生产者发出消息");
    }
}
