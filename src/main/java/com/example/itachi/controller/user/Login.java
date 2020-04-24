package com.example.itachi.controller.user;

import com.example.itachi.config.shiro.ShiroRealm;
import com.example.itachi.entity.User;
import com.example.itachi.mapper.user.UserMapper;
import com.example.itachi.util.Result;
import com.example.itachi.util.ResultCodeUtil;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Login {

    private final UserMapper userMapper;

    public Login(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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

    @RequestMapping("/static/userLogin")
    public Result userLogin(@RequestBody User user){
            log.info("调用了userlogin接口");
            Map<String,Object> map = new HashMap();
            try{
                //验证身份和登陆
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassword());
                //验证成功进行登录操作
                subject.login(token);
                map.put("token",token);
            }catch (IncorrectCredentialsException e) {
                return Result.fail(ResultCodeUtil.WRONG_PASSWORD,"密码错误");
            } catch (LockedAccountException e) {
                return Result.fail(ResultCodeUtil.LOCKED_ACCOUNT,"账号被锁定");
            } catch (AuthenticationException e) {
                return Result.fail(ResultCodeUtil.AUTHENTICATION,"此账号不存在");
            } catch (Exception e) {
                return Result.fail("未知异常，联系管理员");
            }
            //map.put("token",ShiroUtils.getSession().getId().toString());
            String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
            log.info("每次登陆的sessionId:{}",sessionId);
            map.put("sessionId",sessionId);
            //像数据库查询user信息的步骤先暂时省略
            return Result.success(map);
    }

    @RequestMapping("/static/userLogout")
    public Result userLogout(){
        //如果使用redis的话，连同redis中的信息也被清除
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    //在这里测试给测试员加上查询全部用户的权限，验证刷新权限是否成功
    @RequestMapping("/updateUserPermissions")
    public Result updateUserPermissions(){

        //在这里固定值
       /* Integer roleId = 3;
        Integer permissionsId = 1;
        userMapper.updateUserPermissions(roleId, permissionsId);*/
        try {

           /* RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
            //得到了自己的shiroRealm
            ShiroRealm myShiroRealm = (ShiroRealm) rsm.getRealms().iterator().next();

            Subject subject = SecurityUtils.getSubject();
            String realmName = subject.getPrincipals().getRealmNames().iterator().next();
            log.info("realmName:{}", realmName);

            //使用phone+password登陆的 第一个参数改为phone尝试下
            SimplePrincipalCollection principals = new SimplePrincipalCollection(SecurityUtils.getSubject().getPrincipal(), realmName);
            log.info("SecurityUtils.getSubject().getPrincipal():{}", SecurityUtils.getSubject().getPrincipal());

            subject.runAs(principals);
            if (myShiroRealm.isAuthenticationCachingEnabled()) {
                myShiroRealm.getAuthenticationCache().remove(principals);
            }



            subject.releaseRunAs();*/

            ApplicationContext.ge
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Result.success();
    }

}
