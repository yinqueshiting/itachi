package com.example.itachi.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * topic类型的交换机与Queue绑定
 */
@Configuration
public class TopicConfig {

    public static final String TOPIC_EXCHANGE = "topicExchange";

    public static final String TOPIC_QUEUE_ONE = "queueALL";
    public static final String  TOPIC_QUEUE_TWO = "queuePre";
    public static final String  TOPIC_QUEUE_THREE = "queueSuf";
    public static final String  TOPIC_QUEUE_FOUR = "queueFour";

    public static final String TOPIC_BINDING_KEY = "*.rabbitmq.*";
    public static final String TOPIC_BINDING_KEY2 = "*.*.client";
    public static final String TOPIC_BINDING_KEY3 = "com.#";
    //这个用来演示当根据RoutingKey无法找到Q时，Msg是否会在Q中保存等待直到Consumer的连接
    public static final String TOPIC_BINDING_KEY4 = "cn.#";

    //声明topic类型的一个交换机
    @Bean
    public TopicExchange topicExchange(){ return new TopicExchange(TOPIC_EXCHANGE, true, false); }
    //声明Queue
    @Bean
    public Queue topicQueueOne(){ return new Queue(TOPIC_QUEUE_ONE, true); }
    @Bean
    public Queue topicQueueTwo(){ return new Queue(TOPIC_QUEUE_TWO, true); }
    @Bean
    public Queue topicQueueThree(){ return new Queue(TOPIC_QUEUE_THREE, true); }

    @Bean
    public Queue topicQueueFour(){ return new Queue(TOPIC_QUEUE_FOUR, true); }

    //Exchange与Queue进行绑定
    @Bean
    public Binding topicBindOne(){
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(TOPIC_BINDING_KEY);
    }
    @Bean
    public Binding topicBindTwo(){
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(TOPIC_BINDING_KEY2);
    }
    @Bean
    public Binding topicBindThree(){
        return BindingBuilder.bind(topicQueueThree()).to(topicExchange()).with(TOPIC_BINDING_KEY3);
    }
   @Bean
    public Binding topicBindFour(){
        return BindingBuilder.bind(topicQueueFour()).to(topicExchange()).with(TOPIC_BINDING_KEY4);
    }


}
