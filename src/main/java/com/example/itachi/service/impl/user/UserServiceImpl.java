package com.example.itachi.service.impl.user;

import com.example.itachi.entity.Permissions;
import com.example.itachi.entity.Role;
import com.example.itachi.entity.User;
import com.example.itachi.mapper.user.UserMapper;
import com.example.itachi.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryAtLogin(String phone) {

        return userMapper.queryAtLogin(phone);
    }

    @Override
    public List<Permissions> selectPermissionsListByRoleId(Integer roleId) {
        return userMapper.selectPermissionsListByRoleId(roleId);
    }

    @Override
    public List<Role> selectRoleListByUserId(String user_id) {
        return userMapper.selectRoleListByUserId(user_id);
    }
}
