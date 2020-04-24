package com.example.itachi.config.exception;

import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    统一异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleException(Exception e){
        //权限不足
        if (e instanceof UnauthorizedException){
            return Result.unAuthorized();
        }

        log.info("接口异常：{}",e.getMessage());
        return Result.fail(e.getMessage());
    }
}
