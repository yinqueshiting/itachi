package com.example.itachi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.itachi.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TicketPlusMapper extends BaseMapper<Ticket> {
}
