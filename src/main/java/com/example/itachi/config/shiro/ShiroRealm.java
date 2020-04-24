package com.example.itachi.config.shiro;


import com.example.itachi.entity.Permissions;
import com.example.itachi.entity.Role;
import com.example.itachi.entity.User;
import com.example.itachi.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private  UserService userService ;



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
        List<Role> roleList = userService.selectRoleListByUserId(user.getUserId());
        for (Role role : roleList) {
            roleSet.add(role.getRoleMark());
            //根据roleId查询permissions
            List<Permissions> permissionsList = userService.selectPermissionsListByRoleId(role.getId());
            for (Permissions permissions : permissionsList) {
                permissionsSet.add(permissions.getPermissionsName());
            }
        }
        //将角色和权限集合添加到shiro
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionsSet);
        log.info("查询到的权限集合：{}",roleSet);
        log.info("查询到的权限集合：{}",permissionsSet);
        return authorizationInfo;
    }


    //登陆
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String phone =  (String)authenticationToken.getPrincipal(); //身份
        Object passwordByte = authenticationToken .getCredentials();//凭证
        log.info("获取到的phone和password：{},{}",phone,passwordByte);
        //log.info("打印凭证类型：{}",passwordByte.getClass()); 凭证类型：class [C
        //查询用户手机号，盐，密码；
        User user = userService.queryAtLogin(phone);
        if (user == null) {
            throw new AuthenticationException();
        }
        //是否被锁定
        if(!"0".equals(user.getStatus())){
            throw new LockedAccountException();
        }
        //验证密码，如果此时传入的password是明文的话会报错，因为已经设置了HashedCredentialsMatcher加密方式为SHA-256，
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());

        return authenticationInfo;
    }
}
