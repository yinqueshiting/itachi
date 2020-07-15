package com.example.itachi.service;

import com.example.itachi.entity.Ticket;
import com.example.itachi.util.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncService {
    //模拟抢单
    Result addInfo(Ticket ticket,int port) throws InterruptedException;

    //带返回值的异步调用
    Future<Result> futureTaskTest() throws InterruptedException;

    //不阻塞主线程的 异步调用
    CompletableFuture<Result> futureTestTest2() throws InterruptedException;
}
