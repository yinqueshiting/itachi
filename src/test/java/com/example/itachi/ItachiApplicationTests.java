package com.example.itachi;

import com.example.itachi.controller.TestController;
import com.example.itachi.service.AsyncService;
import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.itachi.entity.Ticket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@SpringBootTest
@Slf4j
class ItachiApplicationTests {

    @Resource
    private AsyncService asyncService;

    @Test
    void contextLoads() throws InterruptedException, ExecutionException {

       log.info("主线程运行");
       CompletableFuture<Result> completableFuture = asyncService.futureTestTest2();
       completableFuture.whenComplete(new BiConsumer<Result, Throwable>() {
            @Override
            public void accept(Result result, Throwable throwable) {
                log.info("异步的结果：{}",result);
            }
        });
        log.info("主线程结束");
    }

    /*Future<Result> future = asyncService.futureTaskTest();
       log.info("异步返回：{}",future.get());*/
}
