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

     volatile   int i = 0;

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_ONE)
    public void consumerOne(String json){
        log.info("*.rabbitmq.*接收到的消息体：{}",json);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_TWO)
    public void consumerTwo(String json){
        log.info("*.*.client接收到的消息体：{}",json);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_THREE)
    public void consumerThree(String json){
        log.info("com.#接收到的消息体：{}",json);
    }

    @RabbitListener(queues = TopicConfig.TOPIC_QUEUE_FOUR)
    public void consumerFour(String json, Channel channel, Message message) throws IOException {
        log.info("Mess:{}",message);
        try{
                i++;
                int a = 5/0;

            log.info("cn.#接收到的消息体：{}",json);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            if(e instanceof ArithmeticException){
                log.info("再发一次*********************************");
                //如果是我预计出现的异常则 重试
                //channel.basicRecover(true);
                //将消息放回到队列中
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            }else{

            }

        }

    }


}
