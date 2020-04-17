package com.example.itachi.mapper.user;

import com.example.itachi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {


    User queryAtLogin(@Param("phone") String phone);
}
