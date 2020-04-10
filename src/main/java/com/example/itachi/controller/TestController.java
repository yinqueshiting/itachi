package com.example.itachi.controller;

import com.example.itachi.entity.Test;
import com.example.itachi.service.TestService;
import com.example.itachi.util.Result;
import com.example.itachi.util.validated.InsertValidated;
import com.example.itachi.util.validated.SelectValidated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Test)表控制层
 *
 * @author makejava
 * @since 2020-04-08 11:45:16
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    /**
     * 服务对象
     */
    @Resource
    private TestService testService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{id}")
    public Result selectOne(@PathVariable Integer id) {
        return Result.success(this.testService.queryById(id));
    }

    @PostMapping("test")
    public Result test(){
        return Result.success("hello");
    }

    /*
        新增后返回表的自增主键
     */
    @PostMapping("insertReturnId")
    public Result insertReturnId(@RequestBody  @Validated(InsertValidated.class) Test test){
        log.info("参数：{}",test);
        return Result.success(testService.insertReturnId(test));
    }
    /*
        参数验证
     */
    @PostMapping("selectOneFormMP")
    public Result selectOneFormMP(@RequestBody @Validated(SelectValidated.class) Test test){
        return Result.success(testService.selectOneFormMP(test));
    }

    /**
     * 关于级联查询
     */
    @PostMapping("selectUserTicketLists")
    public Result selectUserTicketLists(@RequestBody @Validated(SelectValidated.class) Test test){
        return Result.success(testService.selectUserTicketLists(test));
    }
}