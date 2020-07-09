package com.example.itachi.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/*
 * topic类型的交换机与Queue绑定
 */
@Configuration
public class TopicConfig {

    //正常交换机
    public static final String TOPIC_EXCHANGE = "topicExchange";
    //死信交换机DLX
    public static final String DEAD_TOPIC_EXCHANGE = "deadTopicExchange";


    public static final String TOPIC_QUEUE_ONE = "queueALL"; //指定这个队列中指定死信交换机
    public static final String  TOPIC_QUEUE_TWO = "queuePre";
    public static final String  TOPIC_QUEUE_THREE = "queueSuf";
    public static final String  TOPIC_QUEUE_FOUR = "queueFour";

    public static final String TOPIC_BINDING_KEY = "*.rabbitmq.*";
    public static final String TOPIC_BINDING_KEY2 = "*.*.client";
    public static final String TOPIC_BINDING_KEY3 = "com.#";
    //当根据RoutingKey无法找到Q时，Msg会在Q中保存等待直到Consumer的连接
    public static final String TOPIC_BINDING_KEY4 = "cn.#";

    //声明一个topic类型的交换机
    @Bean
    public TopicExchange topicExchange(){ return new TopicExchange(TOPIC_EXCHANGE, true, false); }
    //申明一个DLX,就是一个普通交换机
    @Bean
    public TopicExchange deadTopicExchange(){
        return new TopicExchange(DEAD_TOPIC_EXCHANGE, true, false);
    }

    //声明Queue
    @Bean
    public Queue topicQueueOne(){
        Map<String,Object> params = new HashMap<>();
        //指定转发到哪个DLX中去
        params.put("x-dead-letter-exchange",DEAD_TOPIC_EXCHANGE);
        //决定在转发时携带什么RoutingKey，根据BindingKey分发给与DLX绑定的Q，
        params.put("x-dead-letter-routing-key","com.mq.client");
        //统一设置这个队列中所有消息的过期时间
        //params.put("x-message-ttl",5*1000);
        return new Queue(TOPIC_QUEUE_ONE, true,false,false,params);
    }


    @Bean
    public Queue topicQueueTwo(){ return new Queue(TOPIC_QUEUE_TWO, true); }
    @Bean
    public Queue topicQueueThree(){ return new Queue(TOPIC_QUEUE_THREE, true); }

    @Bean
    public Queue topicQueueFour(){ return new Queue(TOPIC_QUEUE_FOUR, true); }

    @Bean
    public Binding topicBindOne(){ return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(TOPIC_BINDING_KEY); }
    //在使用延迟队列的期间，这两个队列暂时订阅DLX  ,也被称为实际消费队列（死信队列），直接定于DLX
    @Bean
    public Binding topicBindTwo(){ return BindingBuilder.bind(topicQueueTwo()).to(deadTopicExchange()).with(TOPIC_BINDING_KEY2); }
    @Bean
    public Binding topicBindThree(){ return BindingBuilder.bind(topicQueueThree()).to(deadTopicExchange()).with(TOPIC_BINDING_KEY3); }
    @Bean
    public Binding topicBindFour(){ return BindingBuilder.bind(topicQueueFour()).to(topicExchange()).with(TOPIC_BINDING_KEY4); }


}
