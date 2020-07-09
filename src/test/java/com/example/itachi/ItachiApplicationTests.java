package com.example.itachi;

import com.example.itachi.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.itachi.entity.Ticket;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class ItachiApplicationTests {


    private static volatile   AtomicInteger num = new AtomicInteger(0);
    static volatile CountDownLatch countDownLatch = new CountDownLatch(30);
    ThreadLocal<Integer> intValue = new ThreadLocal<>();

     @Resource
     private RedisTemplate redisTemplate;

     @Resource
     private TestController testController;

    @Test
    void contextLoads() throws InterruptedException, CloneNotSupportedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
       /* Map map1 = new ConcurrentHashMap();
        redisTemplate.opsForValue().set("key",0);
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(){
                public void run(){
                    for (int j =0; j < 100; j++) {
                        //System.out.println("第："+ finalI +"返回："+map1.put("key",finalI*j));
                        redisTemplate.opsForValue().increment("key");
                    }
                }
            }.start();
            countDownLatch.countDown();
        }
        countDownLatch.await();
        System.out.println(redisTemplate.opsForValue().get("key")+"***********************************");*/

      /*  Map map = new HashMap();
        map.put("key","value1");
        System.out.println(map.putIfAbsent("key","value2"));
        map.get("key");
        System.out.println(map);

        */

      Class clazz = TestController.class;
      Method method = clazz.getMethod("selectOne",Integer.class);
      //获取类对象
       // Object obj = clazz.newInstance();
       Method[] methods = clazz.getMethods();
       System.out.println(method.invoke(testController,1));


    }

}
