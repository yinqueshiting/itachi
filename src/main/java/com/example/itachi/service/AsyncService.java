package com.example.itachi.service;

import com.example.itachi.entity.Ticket;
import com.example.itachi.util.Result;

public interface AsyncService {
    //模拟抢单
    Result addInfo(Ticket ticket,int port) throws InterruptedException;
}
