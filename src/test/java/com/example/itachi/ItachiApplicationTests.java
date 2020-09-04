package com.example.itachi;

import com.alibaba.fastjson.JSONObject;
import com.example.itachi.controller.TestController;
import com.example.itachi.service.AsyncService;
import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.itachi.entity.Ticket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@SpringBootTest
@Slf4j
class ItachiApplicationTests {

    @Resource
    private AsyncService asyncService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() throws InterruptedException, ExecutionException {
      /*  Ticket ticket = new Ticket();
        ticket.setId(222);ticket.setTicketName("票");ticket.setTickerAmount(588);

        redisTemplate.opsForSet().add("set",ticket);
        //redisTemplate.opsForSet().pop 返回的是LinkHashMap类型,转string 再转成Ticket对象。
        Ticket tt = JSONObject.parseObject(JSONObject.toJSONString(redisTemplate.opsForSet().pop("set")),Ticket.class);
        System.out.println(tt.getTickerAmount());*/
        //System.out.println(redisTemplate.opsForSet().pop("set").getClass());

        //System.out.println(redisTemplate.opsForValue().increment("time_amount"));

        Ticket source = new Ticket();
        source.setId(222);source.setTicketName("票");source.setTickerAmount(588);

        Ticket target = new Ticket();
        BeanUtils.copyProperties(source,target);
        System.out.println(target.getTickerAmount());
        target.setId(22225555);
        System.out.println(source.getId());
        System.out.println(target== source);

    }

    /*Future<Result> future = asyncService.futureTaskTest();
       log.info("异步返回：{}",future.get());*/
}
