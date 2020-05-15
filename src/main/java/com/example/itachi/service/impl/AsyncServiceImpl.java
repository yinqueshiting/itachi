package com.example.itachi.service.impl;

import com.example.itachi.entity.Ticket;
import com.example.itachi.mapper.TicketRecordMapper;
import com.example.itachi.service.AsyncService;
import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private final TicketRecordMapper ticketRecordMapper;

    public AsyncServiceImpl(TicketRecordMapper ticketRecordMapper) {
        this.ticketRecordMapper = ticketRecordMapper;
    }

    @Override
    @Async
    public Result addInfo(Ticket ticket,int port) throws InterruptedException {
        log.info("调用了异步addInfo");
        ticketRecordMapper.addRecord(port);
        return null;
    }
}
