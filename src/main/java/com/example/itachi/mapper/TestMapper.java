package com.example.itachi.mapper;

import com.example.itachi.entity.Test;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Test)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-08 11:41:40
 */
@Repository
public interface TestMapper {
    Test selectOne(@Param("id") Integer id);

    int insertTest(@Param("test") Test test);

    Test selectUserTicketLists(@Param("user_id") String userId);
}