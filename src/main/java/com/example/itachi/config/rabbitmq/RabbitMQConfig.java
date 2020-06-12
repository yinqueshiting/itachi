package com.example.itachi.config.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;



    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        //设置confirm
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //设置return
        factory.setPublisherReturns(true);
        //设置ACK机制
        //factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //消息发送、return、确认机制都需要在这里设置
        template.setConfirmCallback(rabbitmqConfirmConfig());
        template.setReturnCallback(rabbitmqReturnConfig());
        return template;
    }

    @Bean
    public RabbitmqConfirmConfig rabbitmqConfirmConfig(){
        return new RabbitmqConfirmConfig();
    }

    @Bean
    public RabbitmqReturnConfig rabbitmqReturnConfig(){
        return new RabbitmqReturnConfig();
    }


}
