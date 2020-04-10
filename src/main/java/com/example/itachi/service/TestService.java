package com.example.itachi.service;

import com.example.itachi.entity.Test;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Test)表服务接口
 *
 * @author makejava
 * @since 2020-04-08 11:41:46
 */
public interface TestService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Test queryById(Integer id);

    Test insertReturnId(Test test);


    Test selectOneFormMP(Test test);

    Test selectUserTicketLists(Test test);
}