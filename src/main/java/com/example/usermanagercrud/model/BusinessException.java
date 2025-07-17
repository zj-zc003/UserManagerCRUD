package com.example.usermanagercrud.model;

import com.example.usermanagercrud.dto.R;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// BusinessException.java
public class BusinessException extends RuntimeException {
    
    private int code = 500;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}


