package com.example.itachi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.itachi.entity.User;
import com.example.itachi.entity.Ticket;
import com.example.itachi.mapper.TestMapper;
import com.example.itachi.mapper.TestPlusMapper;
import com.example.itachi.mapper.TicketPlusMapper;
import com.example.itachi.service.AsyncService;
import com.example.itachi.service.TestService;
import com.example.itachi.util.Result;
import com.example.itachi.util.ResultCodeUtil;
import com.example.itachi.util.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Random;


/**
 * (Test)表服务实现类
 *
 * @author makejava
 * @since 2020-04-08 11:41:47
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    private final TestMapper testMapper;

    public TestServiceImpl(TestMapper testMapper, TestPlusMapper testPlusMapper, TicketPlusMapper ticketPlusMapper, RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate, AsyncService asyncService) {
        this.testMapper = testMapper;
        this.testPlusMapper = testPlusMapper;
        this.ticketPlusMapper = ticketPlusMapper;
        this.redisTemplate = redisTemplate;
        this.asyncService = asyncService;
    }

    private final String HOT_ACTIVITIES_KEY = "hotActivities";

    private final TestPlusMapper testPlusMapper;

    private final TicketPlusMapper ticketPlusMapper;

    private final RedisTemplate redisTemplate;

    private final AsyncService asyncService;

    @Override
    public User queryById(Integer id) {
        User test = new User();
        test.setId(id);
        return this.testMapper.selectOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User insertReturnId(User test) {
        //生成随机盐
        String salt = SHA256Util.getSalt();
        test.setSalt(salt);
        //得到哈希后的密码
        test.setPassword(SHA256Util.sha256(salt,test.getPassword()));
        log.info("新增时的用户：{}",test);
        //testPlusMapper.insert(test); 由于mybatis-plus的insert()原理不熟悉 （应该有一次bean序列化的过程 导致password user_id这些参数不能新增成功） 改用手动写insert的sql

        //获取当前时间戳+随机的生成一个数，组成用户id（用于前端展示和修改删除）
        test.setUserId(String.valueOf(System.currentTimeMillis()+new Random().nextInt(1000)));
        testMapper.insertTest(test);
        int increaseId = test.getId();
        return testPlusMapper.selectById(increaseId);
    }

    @Override
    public User selectOneFormMP(User test) {
        Integer id = test.getId();
        //MybatisPlus的EntityWrapper 在3.0版本以上就没发现了，改为了QueryWrapper
        return testPlusMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "selectUserTicketLists",key = "#root.args[0].page+':'+#root.args[0].rows")
    public User selectUserTicketLists(User test) {
        log.info("进入方法内部");
        String userId = test.getUserId();
        Integer page = test.getPage()==null?1:test.getPage();
        Integer rows = test.getRows()==null?10:test.getRows();
        User userTicketLists = testMapper.selectUserTicketLists(userId,(page-1)*rows,rows);
        return userTicketLists;
    }

    @Override
    public List<User> selectUserLists() {
        List<User> userLists = testMapper.selectUserLists();
        return userLists;
    }

    @Override
    public Ticket selectSingleTicketInfo(Ticket ticket) {
        Integer ticketId = ticket.getId();
        return ticketPlusMapper.selectById(ticketId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(allEntries = true,value = "selectUserTicketLists")
    public Integer updateTicketInfo(Ticket ticket) {
        Ticket updateTicket = new Ticket();
        updateTicket.setId(ticket.getId());
        QueryWrapper<Ticket> updateWrapper = new QueryWrapper<>(updateTicket);
        int updateRows = ticketPlusMapper.update(ticket,updateWrapper);
        return updateRows;
    }

    /**
     * 这是第一个版本，没有在释放时判断当前锁是否为此线程锁加上的，不应该出现A线程释放了B锁的情况
     * @param ticket
     * @param port
     * @return
     * @throws InterruptedException
     */
   /* @Override
    public Result hotActivities(Ticket ticket,int port) throws InterruptedException {
        //后续再来考虑 解锁和加锁保证是同一客户端的问题

        if(redisTemplate.opsForValue().setIfAbsent("hotActivities",0,Duration.ofSeconds(3))) {
            Integer ticket_amount = (Integer) redisTemplate.opsForValue().get("time_amount");
            if (ticket_amount>0){
                redisTemplate.opsForValue().decrement("time_amount");
                //调用异步的线程去对数据库进行写入
                asyncService.addInfo(ticket,port);
            }else{
                return Result.fail(ResultCodeUtil.INSUFFICIENT_INVENTORY,"库存不足！");
            }
            //最后一定要释放锁 优化时应该在finally里进行
            redisTemplate.delete("hotActivities");
        }else{
            return Result.fail(ResultCodeUtil.TRY_AGAIN_LATER,"请稍后再试");
        }
        return Result.success();
    }*/

        //在这里 get 和 decrement不是一个原子操作 也会出现问题
       /* Integer ticket_amount = (Integer) redisTemplate.opsForValue().get("time_amount");
        if (ticket_amount > 0) {
            redisTemplate.opsForValue().decrement("time_amount");
            //调用异步的线程去对数据库进行写入
            asyncService.addInfo(ticket, port);
        } else {
            return Result.fail(ResultCodeUtil.INSUFFICIENT_INVENTORY, "库存不足！");
        }*/

       //如果说A线程加锁成功后，异常退出了 那是不是后续的所有线程都需要等待A自动过期
    @Override
    public Result hotActivities(Ticket ticket,int port) throws InterruptedException {
        //random_value作为全局的唯一值，如果在多机器实例上想保证唯一可以采用UUID
        Long random_value = System.currentTimeMillis();
        //setIfAbsent返回Boolean类型 ，如果原key不存在才会true，key已存在则不会做任何修改
        try {
            if (redisTemplate.opsForValue().setIfAbsent(HOT_ACTIVITIES_KEY, random_value, Duration.ofSeconds(30))) {
                log.info("就只一句话");
               //这里是先执行扣减再去判断，所以当Redis中的time_amount为-1时，则说明库存已经卖完了
               Long residualAmount = redisTemplate.opsForValue().decrement("time_amount");
                if (residualAmount >= 0) {
                    //调用异步的线程去对数据库进行写入
                    asyncService.addInfo(ticket, port);
                } else {
                    return Result.fail(ResultCodeUtil.INSUFFICIENT_INVENTORY, "库存不足！");
                }
            } else {
                return Result.fail(ResultCodeUtil.TRY_AGAIN_LATER, "请稍后再试");
            }
        }finally {
            //有说 获取和删除不是一个原子性的操作， 可以使用RedissonLock 来使用，使用起来类似于ReentrantLock 一样tryLock ，unLock
            //redissonLock是使用LUA脚本来完成 保证原子性；针对并发不高的情况还是setnx来做
            if(random_value.equals(redisTemplate.opsForValue().get(HOT_ACTIVITIES_KEY)))
                redisTemplate.delete(HOT_ACTIVITIES_KEY);
        }

        return Result.success();
    }

}