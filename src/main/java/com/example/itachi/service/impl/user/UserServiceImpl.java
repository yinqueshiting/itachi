package com.example.itachi.service.impl.user;

import com.example.itachi.entity.User;
import com.example.itachi.mapper.user.UserMapper;
import com.example.itachi.service.user.UserService;
import org.springframework.stereotype.Service;

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
}
