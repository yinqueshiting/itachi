package com.example.itachi.service.user;

import com.example.itachi.entity.User;

public interface UserService {
    //用于登陆时查询密码跟盐
    User queryAtLogin(String phone);
    //
    //List<>
}
