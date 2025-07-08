package com.example.usermanagercrud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class SysUser {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private String avatar;
    private String realName;
    private Integer gender;
    private Date birthday;
    private Integer status;
    private Integer userType;
    private Integer isVerified;
    private String inviteCode;
    private Long invitedBy;
    private Date createTime;
    private Date updateTime;
}
