package com.example.itachi.entity;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CorrelationData;

@Data
public class CustomCorrelationData extends CorrelationData {
    //消息体
    private Object message;
    //交换机名称
    private String exchange;
    //路由键
    private String routingKey;
    //重试次数
    private int retryCount = 0;

}
