package com.example.usermanagercrud.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace(); // 打印堆栈信息到控制台
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("服务器错误: " + e.getMessage());
    }
}
