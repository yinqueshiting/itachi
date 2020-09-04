package com.example.itachi.service.impl;

import com.example.itachi.entity.Ticket;
import com.example.itachi.mapper.TicketRecordMapper;
import com.example.itachi.service.AsyncService;
import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.FutureResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private final TicketRecordMapper ticketRecordMapper;

    public AsyncServiceImpl(TicketRecordMapper ticketRecordMapper) {
        this.ticketRecordMapper = ticketRecordMapper;
    }

    @Override
    @Async("taskExecutor")
    public Result addInfo(Ticket ticket,int port) throws InterruptedException {
        log.info("调用了异步addInfo");
        ticketRecordMapper.addRecord(port);
        return null;
    }

    @Async("taskExecutor")
    @Override
    public Future<Result> futureTaskTest() throws InterruptedException {
        log.info("进入futureTaskTest,开始Sleep");
        Thread.sleep(5000);
        log.info("futureTaskTest Sleep结束");
        return AsyncResult.forValue(Result.success());
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<Result> futureTestTest2() throws InterruptedException {
        log.info("futureTestTest2,开始Sleep");
        int k = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <100000 ; i++) {
            sb.append(k);
        }
        log.info("futureTestTest2 Sleep结束");
        return CompletableFuture.completedFuture(Result.success());
    }
}
