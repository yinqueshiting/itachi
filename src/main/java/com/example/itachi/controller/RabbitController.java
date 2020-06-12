package com.example.itachi.controller;

import com.example.itachi.queue.TopicProducer;
import com.example.itachi.util.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class RabbitController {

    @Resource
    private TopicProducer topicProducer;

    @PostMapping("topicTest")
    public Result topicTest(@RequestBody Map<String,String> paramMap){
        String routingKey = paramMap.get("routingKey");
        String json = paramMap.get("json");
        topicProducer.send(routingKey,json);
        return Result.success();
    }

}
