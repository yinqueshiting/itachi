/*
package com.example.itachi.config.security;

import com.example.itachi.entity.Role;
import com.example.itachi.entity.User;
import com.example.itachi.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetails implements UserDetailsService {


    private final UserMapper userMapper;

    public UserDetails(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    */
/*
        用于获取一个UserDetails对象，此对象包含了一系列在验证时会用到的信息，包括用户名、密码、权限以及其它信息，Spring Security会根据这些信息判断验证是否成功
     *//*

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        log.info("调用loadUserByUsername");
        //创建一个放role的列表，每种角色对应一个GrantedAuthority
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //查询用户是否存在
        User user = userMapper.queryAtLogin(phone);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询权限并添加到authorityList
        List<Role> roleList = userMapper.selectRoleListByUserId(user.getUserId());
        for (Role role : roleList) {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleMark()));
        }


        return new org.springframework.security.core.userdetails.User(phone,user.getPassword2(),authorityList);
    }
}
*/
