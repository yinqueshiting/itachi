package com.example.itachi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.itachi.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestPlusMapper extends BaseMapper<Test> {

}
