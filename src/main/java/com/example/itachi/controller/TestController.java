package com.example.itachi.controller;

import com.example.itachi.entity.User;
import com.example.itachi.entity.Ticket;
import com.example.itachi.service.AsyncService;
import com.example.itachi.service.TestService;
import com.example.itachi.util.Result;
import com.example.itachi.util.validated.InsertValidated;
import com.example.itachi.util.validated.SelectValidated;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    private final AsyncService asyncService;

    private volatile StringBuilder str = new StringBuilder("ABC");

    public TestController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

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
        log.info("selectUserTicketLists:{}",test);
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
        redisTemplate.opsForSet().add(user.getId().toString(),user);
        redisTemplate.opsForHash().put("hash",user.getId().toString(),user);
        redisTemplate.opsForList().leftPush("lst"+user.getId(),user);
        //randomMember
        return Result.success(redisTemplate.opsForSet().randomMember("12255"));
    }
    @PostMapping("debugTest")
    public Result debugTest(){
        if(str.indexOf("F")==str.length()-1){
            return Result.success("已经添加过了");
        }
        str.append("DEF");
        return Result.success("修改后成为ABCDEF");
    }

    @PostMapping("hotActivities")
    public Result hotActivities(Ticket ticket, HttpServletRequest request){

        try {
            //获取端口号
            int port = request.getLocalPort();
            return testService.hotActivities(ticket,port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success("成功");
    }

}