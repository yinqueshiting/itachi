package com.example.itachi.queue;

import com.example.itachi.config.rabbitmq.TopicConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TopicConsumer {

     volatile int i = 0;

     //在使用延迟队列时，该C订阅的Q被改成了缓冲队列 不被C订阅
    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_ONE)
    public void consumerOne(Channel channel,String json, Message message) throws IOException {
        log.info("*.rabbitmq.*接收到的消息体：{}",json);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //        //现在选择拒绝消息，让消息被转发到DLX交换机中去
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_TWO)
    public void consumerTwo(Channel channel,String json, Message message) throws IOException {
        log.info("*.*.client接收到的消息体：{}",json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_THREE)
    public void consumerThree(Channel channel,String json, Message message) throws IOException {
        log.info("com.#接收到的消息体：{}",json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_FOUR)
    public void consumerFour(String json, Channel channel, Message message) throws IOException {
        log.info("Mess:{}",message);
        //模拟出现一个runtimeException，测试配置的重试机制

        log.info("cn.#接收到的消息体：{}",json);
        //手动进行回复 消息处理完毕
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //拒绝消息
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);

        //false直接拒绝，并不持久化到Q中，true为放置在Q中处于unack状态
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);




    }


}
