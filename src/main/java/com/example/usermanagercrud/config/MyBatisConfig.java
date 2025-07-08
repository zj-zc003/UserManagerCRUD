package com.example.usermanagercrud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.usermanagercrud.mapper")
public class MyBatisConfig {
    // 配置类（无需额外代码）
}
