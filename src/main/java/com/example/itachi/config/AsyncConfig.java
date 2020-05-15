package com.example.itachi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {
    private static final Integer CORE_POOL_SIZE = 6;

    private static final Integer MAX_POOL_SIZE = 10;

    private static final Integer QUEUE_CAPACITY = 100;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数：定义了最小可以同时运行的线程数量
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数：当队列中存档的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //队列大小：判断当前运行的线程数量是否达到核心线程数，如果达到，存放到队列中
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //当最大池已满时，此策略保证不会丢失任务请求，但是可能会影响应用程序整体性能
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("My ThreadPoolTaskExecutor-");
        executor.initialize();
        return executor;
    }

}
