package com.example.itachi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.itachi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TestPlusMapper extends BaseMapper<User> {

}
