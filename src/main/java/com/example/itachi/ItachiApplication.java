package com.example.itachi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.itachi.*"})
@MapperScan(basePackages = {"com.example.itachi.mapper"})
@ServletComponentScan("com.example.itachi.filter")
@EnableCaching
@EnableAsync //开启异步线程
public class ItachiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItachiApplication.class, args);
    }

}
