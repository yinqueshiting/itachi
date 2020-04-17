package com.example.itachi.config.shiro;


import com.example.itachi.entity.User;
import com.example.itachi.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {


    private final UserService userService;

    public ShiroRealm(UserService userService) {
        this.userService = userService;
    }

    //授权：查询角色和权限的集合
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        log.info("当前登陆者的信息：{}",user);
        //用于存放用户的角色和权限的列表
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionsSet = new HashSet<>();
        //查询角色的集合


        return null;
    }

    //登陆
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String phone =  (String)authenticationToken.getPrincipal(); //身份
        String password = (String)authenticationToken.getCredentials();//凭证
        log.info("获取到的phone和password：{},{}",phone,password);
        //查询用户手机号，盐，密码；
        User user = userService.queryAtLogin(phone);
        if (user == null) {
            throw new AuthenticationException();
        }
        if(!"0".equals(user.getStatus())){
            throw new LockedAccountException();
        }
        //验证密码
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,password, ByteSource.Util.bytes(user.getSalt()),getName());

        return authenticationInfo;
    }
}
