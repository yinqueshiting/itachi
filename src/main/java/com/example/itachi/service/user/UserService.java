package com.example.itachi.service.user;

import com.example.itachi.entity.Permissions;
import com.example.itachi.entity.Role;
import com.example.itachi.entity.User;

import java.util.List;

public interface UserService {
    //用于登陆时查询密码跟盐
    User queryAtLogin(String phone);
    //shiro在授权时查询role列表
    List<Role> selectRoleListByUserId(String user_id);

    //shiro在授权时查询permissions列表
    List<Permissions> selectPermissionsListByRoleId(Integer roleId);

}
