package com.example.itachi.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
    作为结果集的统一返回
 */
@Data
public class Result implements Serializable {
    //构造器私有化
    private Result(){};

    //通知请求是否正常执行
    private boolean request_is_sussess;
    //返回结果码
    private String result_code;
    //结果的详细说明
    private String result_message;
    //返回的数据
    private Object data;

    //请求正常执行完毕，不带数据返回 （一般用作于验证类接口）
    public static Result success(){
        Result result = new Result();
        result.setRequest_is_sussess(true);
        result.setResult_code(ResultCodeUtil.SUCCESS);
        result.setResult_message("SUCCESS");
        return result;
    }
    //请求正常执行完毕 携带数据的返回
    public static Result success(Object data){
        Result result = new Result();
        result.setRequest_is_sussess(true);
        result.setResult_code(ResultCodeUtil.SUCCESS);
        result.setResult_message("SUCCESS");
        result.setData(data);
        return result;
    }
    //请求在某处异常败 不携带数据
    public static Result fail(){
        Result result = new Result();
        result.setRequest_is_sussess(false);
        result.setResult_code(ResultCodeUtil.FAIL);
        result.setResult_message("FAIL");
        return result;
    }
    public static Result fail(String result_message){
        Result result = new Result();
        result.setRequest_is_sussess(false);
        result.setResult_code(ResultCodeUtil.FAIL);
        result.setResult_message(result_message);
        //result.setResult_message("FAIL");
        return result;
    }

    /**
     * 未登录
     * @param
     * @return
     */
    public static Result notLogin(){
        Result result = new Result();
        result.setRequest_is_sussess(false);
        result.setResult_code(ResultCodeUtil.NOT_LOGIN);
        result.setResult_message("未登录");
        return result;
    }
    /**
     * 没有操作权限
     * @param
     * @return
     */
    public static Result unAuthorized(){
        Result result = new Result();
        result.setRequest_is_sussess(false);
        result.setResult_code(ResultCodeUtil.UNAUTHORIZED);
        result.setResult_message("没有操作权限");
        return result;
    }
}
