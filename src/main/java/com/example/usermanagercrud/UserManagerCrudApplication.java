package com.example.usermanagercrud;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(
        basePackages = "com.example.usermanagercrud.mapper",
        annotationClass = Mapper.class
)
public class UserManagerCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerCrudApplication.class, args);
    }

}
