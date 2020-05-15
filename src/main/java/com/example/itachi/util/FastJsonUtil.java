package com.example.itachi.util;

import com.alibaba.fastjson.JSON;
import com.example.itachi.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FastJsonUtil {

    /*
        把java对象转Json字符串
     */
    public static String ObjectToJson(Object object){
        return JSON.toJSONString(object);
    }

    /*
        Json字符串转java对象
     */
    public static Object JsonStringToObject(String jsonString,Class c){
        return JSON.parseObject(jsonString, c);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setSalt("jjj");
        user.setId(52);
        String text = ObjectToJson(user);
        log.info("转成json字符串:{}",text);
        log.info("json字符串转对象:{}",JsonStringToObject(text,User.class));
    }
}
