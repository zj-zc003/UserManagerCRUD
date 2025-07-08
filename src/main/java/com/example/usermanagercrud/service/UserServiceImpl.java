package com.example.usermanagercrud.service;

import com.example.usermanagercrud.entity.SysUser;
import com.example.usermanagercrud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int addUser(SysUser user) {
        return userMapper.insertUser(user);
    }

    @Override
    public SysUser getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<SysUser> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public int updateUser(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(Long userId) {
        return userMapper.deleteById(userId);
    }
}
