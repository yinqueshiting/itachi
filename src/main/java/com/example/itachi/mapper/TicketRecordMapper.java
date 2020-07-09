package com.example.itachi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TicketRecordMapper {

    @Insert("insert into ticket_record(port) values(#{port})")
    void addRecord(@Param("port") int port);
}
