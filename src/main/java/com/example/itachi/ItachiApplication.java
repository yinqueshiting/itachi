package com.example.itachi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.itachi.*"})
@MapperScan(basePackages = {"com.example.itachi.mapper"})
@ServletComponentScan("com.example.itachi.filter")
public class ItachiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItachiApplication.class, args);
    }

}
