package com.example.usermanagercrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// R.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private int code;
    private String msg;
    private T data;
    
    // 成功响应
    public static <T> R<T> success() {
        return new R<>(200, "success", null);
    }
    
    public static <T> R<T> success(T data) {
        return new R<>(200, "success", data);
    }
    
    // 失败响应
    public static <T> R<T> fail(String msg) {
        return new R<>(500, msg, null);
    }
    
    public static <T> R<T> fail(String msg, int code) {
        return new R<>(code, msg, null);
    }
}
