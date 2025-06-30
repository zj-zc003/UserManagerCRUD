package com.example.usermanagercrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {
    private Boolean success;
    private String message;
}
