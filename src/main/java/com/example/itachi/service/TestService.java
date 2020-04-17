package com.example.itachi.service;

import com.example.itachi.entity.User;
import com.example.itachi.entity.Ticket;

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
    User queryById(Integer id);

    User insertReturnId(User test);


    User selectOneFormMP(User test);

    User selectUserTicketLists(User test);

    List<User> selectUserLists();

    Ticket selectSingleTicketInfo(Ticket ticket);

    Integer updateTicketInfo(Ticket ticket);
}