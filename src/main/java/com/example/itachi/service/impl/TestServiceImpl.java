package com.example.itachi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.itachi.entity.Test;
import com.example.itachi.mapper.TestMapper;
import com.example.itachi.mapper.TestPlusMapper;
import com.example.itachi.service.TestService;
import com.example.itachi.util.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


/**
 * (Test)表服务实现类
 *
 * @author makejava
 * @since 2020-04-08 11:41:47
 */
@Service("testService")
@Slf4j
public class TestServiceImpl implements TestService {
    private final TestMapper testMapper;

    public TestServiceImpl(TestMapper testMapper, TestPlusMapper testPlusMapper) {
        this.testMapper = testMapper;
        this.testPlusMapper = testPlusMapper;
    }

    private final TestPlusMapper testPlusMapper;

    @Override
    public Test queryById(Integer id) {
        Test test = new Test();
        test.setId(id);
        return this.testMapper.selectOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Test insertReturnId(Test test) {
        //生成随机盐
        String salt = SHA256Util.getSalt();
        test.setSalt(salt);
        //得到哈希后的密码
        test.setPassword(SHA256Util.sha256(salt,test.getPassword()));
        log.info("新增时的用户：{}",test);
        //testPlusMapper.insert(test); 由于mybatis-plus的insert()原理不熟悉 （应该有一次bean序列化的过程 导致password user_id这些参数不能新增成功） 改用手动写insert的sql
        test.setUserId(String.valueOf(System.currentTimeMillis()+new Random().nextInt(1000)));
        testMapper.insertTest(test);
        int increaseId = test.getId();
        return testPlusMapper.selectById(increaseId);
    }

    @Override
    public Test selectOneFormMP(Test test) {
        Integer id = test.getId();
        //MybatisPlus的EntityWrapper 在3.0版本以上就没发现了，改为了QueryWrapper

        return testPlusMapper.selectById(id);
    }

    @Override
    public Test selectUserTicketLists(Test test) {
        String userId = test.getUserId();
        log.info("selectUserTicketLists验证testMapper：{}",testMapper);
        Test userTicketLists = testMapper.selectUserTicketLists(userId);
        return userTicketLists;
    }
}