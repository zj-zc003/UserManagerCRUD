package com.example.usermanagercrud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usermanagercrud.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT user_id, password, status FROM sys_user WHERE username = #{username} LIMIT 1")
    SysUser findByUsername(@Param("username") String username);
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND password = #{password} LIMIT 1")
    SysUser findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}

