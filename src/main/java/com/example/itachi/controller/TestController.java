package com.example.itachi.controller;

import com.example.itachi.entity.User;
import com.example.itachi.entity.Ticket;
import com.example.itachi.service.TestService;
import com.example.itachi.util.Result;
import com.example.itachi.util.validated.InsertValidated;
import com.example.itachi.util.validated.SelectValidated;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

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
    public Result insertReturnId(@RequestBody  @Validated(InsertValidated.class) User test){
        log.info("参数：{}",test);
        return Result.success(testService.insertReturnId(test));
    }
    /*
        参数验证
     */
    @PostMapping("selectOneFormMP")
    public Result selectOneFormMP(@RequestBody @Validated(SelectValidated.class) User test){
        return Result.success(testService.selectOneFormMP(test));
    }

    /**
     * 关于级联查询
     */
    @PostMapping("selectUserTicketLists")
    public Result selectUserTicketLists(@RequestBody @Validated(SelectValidated.class) User test){
        return Result.success(testService.selectUserTicketLists(test));
    }

    /**
     *  返回单个Bean的列表
     */
    @PostMapping("selectUserLists")
    @RequiresPermissions(value = "selectLists")
    public Result selectUserLists(){
        return Result.success(testService.selectUserLists());
    }



    /*
        查询单个票信息 使用@TableName("ticket")后不需要再对每一个字段标注@Column
     */
    @PostMapping("selectSingleTicketInfo")
    public Result selectSingleTicketInfo(@RequestBody Ticket ticket){
        return Result.success(testService.selectSingleTicketInfo(ticket));
    }

    /**
     * 测试修改
     */
    @PostMapping("updateTicketInfo")
    public Result updateTicketInfo(@RequestBody Ticket ticket){
        return Result.success(testService.updateTicketInfo(ticket));
    }

    @PostMapping("redisTest")
    public Result redisTest(){
        User user = new User();
        user.setId(12255);
        user.setSalt("5021");

        redisTemplate.opsForSet().add(user.getId(),user);
        //randomMember
        return Result.success(redisTemplate.opsForSet().randomMember(12255));
    }
}