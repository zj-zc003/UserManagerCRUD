package com.example.usermanagercrud.service;

import com.example.usermanagercrud.entity.SysUser;

import java.util.List;

public interface UserService {
    int addUser(SysUser user);
    SysUser getUserById(Long userId);
    List<SysUser> getAllUsers();
    int updateUser(SysUser user);
    int deleteUser(Long userId);
}
