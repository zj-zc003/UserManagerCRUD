package com.example.usermanagercrud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.usermanagercrud.dto.LoginDTO;
import com.example.usermanagercrud.dto.LoginResult;
import com.example.usermanagercrud.entity.SysUser;
import com.example.usermanagercrud.mapper.SysUserMapper;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private SysUserMapper userMapper;

    public LoginResult login(LoginDTO dto) {
        try {
            // 1. 参数基础校验
            if (StringUtils.isBlank(dto.getUsername()) || dto.getUsername().length() > 50) {
                return new LoginResult(false, "用户名格式错误");
            }
            if (StringUtils.isBlank(dto.getPassword()) || dto.getPassword().length() > 100) {
                return new LoginResult(false, "密码格式错误");
            }

            // 2. 安全查询 - 使用参数化查询防止SQL注入
            SysUser user = userMapper.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

            // 3. 统一返回模糊错误信息
            if (user == null || !dto.getPassword().equals(user.getPassword())) {
                logger.warn("登录失败尝试: username={}", dto.getUsername());
                return new LoginResult(false, "用户名或密码错误");
            }

            // 4. 状态检查
            if (user.getStatus() == 0) {
                return new LoginResult(false, "用户已被禁用");
            }

            return new LoginResult(true, "登录成功", user.getUserId());
        } catch (Exception e) {
            logger.error("登录异常: {}", e.getMessage(), e);
            return new LoginResult(false, "系统异常，请稍后重试");
        }
    }
}

