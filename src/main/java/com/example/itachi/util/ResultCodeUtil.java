package com.example.itachi.util;

/*
    存放返回结果
 */
public class ResultCodeUtil {

    public static final String SUCCESS = "0000";

    public static final String FAIL = "0001";
    //未登录
    public static final String NOT_LOGIN= "0002";
    //没有权限
    public static final String UNAUTHORIZED  = "0003";
    //没有密码错误
    public static final String WRONG_PASSWORD  = "0004";
    //账号被锁定
    public static final String LOCKED_ACCOUNT  = "0005";
    //账号不存在
    public static final String  AUTHENTICATION  = "0006";
}
