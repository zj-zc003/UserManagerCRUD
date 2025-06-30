package com.example.usermanagercrud.controller;

import com.example.usermanagercrud.dto.LoginDTO;
import com.example.usermanagercrud.dto.LoginResult;
import com.example.usermanagercrud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
