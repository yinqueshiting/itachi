package com.example.itachi.controller.user;

import com.example.itachi.entity.User;
import com.example.itachi.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Login {

    /**
     * 未登录的shiro来请求这个接口
     * @return
     */
    @RequestMapping("/static/toLogin")
    public Result notLogin(){
        return Result.notLogin();
    }

    /**
     * 没有权限的来请求这个接口
     * @return
     */
    @RequestMapping("/static/unAuthorized")
    public Result unAuthorized(){
        return Result.unAuthorized();
    }

    @RequestMapping("/static/userlogin")
    public Result userlogin(@RequestBody User user){
        log.info("调用了userlogin接口");
        Map<String,String> map = new HashMap();
        try{
            //验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassword());
            //验证成功进行登录操作
            subject.login(token);
        }catch (IncorrectCredentialsException e) {
            map.put("code","501");
            map.put("msg","密码错误");
            return Result.success(map);
        } catch (LockedAccountException e) {
            map.put("code","502");
            map.put("msg","登录失败，该用户已被冻结");
            return Result.success(map);
        } catch (AuthenticationException e) {
            map.put("code","503");
            map.put("msg","该用户不存在");
            return Result.success(map);
        } catch (Exception e) {
            map.put("code","504");
            map.put("msg","未知异常");
            return Result.success(map);
        }
        map.put("code","000");
        map.put("msg","登录成功");
        //map.put("token",ShiroUtils.getSession().getId().toString());
        log.info("每次登陆的sessionid:{}", SecurityUtils.getSubject().getSession().getId().toString());
        return Result.success(map);
    }

}
