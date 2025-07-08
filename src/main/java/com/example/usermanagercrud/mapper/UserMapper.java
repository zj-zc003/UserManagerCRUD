package com.example.usermanagercrud.mapper;

import com.example.usermanagercrud.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 新增用户
    @Insert("INSERT INTO sys_user(username, password, email, phone, nickname, avatar, " +
            "real_name, gender, birthday, status, user_type, is_verified, invite_code, invited_by) " +
            "VALUES(#{username}, #{password}, #{email}, #{phone}, #{nickname}, #{avatar}, " +
            "#{realName}, #{gender}, #{birthday}, #{status}, #{userType}, #{isVerified}, #{inviteCode}, #{invitedBy})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(SysUser user);

    // 根据ID查询
    @Select("SELECT * FROM sys_user WHERE user_id = #{userId}")
    SysUser selectById(Long userId);

    // 查询所有用户
    @Select("SELECT * FROM sys_user")
    List<SysUser> selectAll();

    // 更新用户
    @Update("UPDATE sys_user SET " +
            "username=#{username}, email=#{email}, phone=#{phone}, nickname=#{nickname}, " +
            "avatar=#{avatar}, real_name=#{realName}, gender=#{gender}, birthday=#{birthday}, " +
            "status=#{status}, user_type=#{userType}, is_verified=#{isVerified}, " +
            "invite_code=#{inviteCode}, invited_by=#{invitedBy} " +
            "WHERE user_id = #{userId}")
    int updateUser(SysUser user);

    // 删除用户
    @Delete("DELETE FROM sys_user WHERE user_id = #{userId}")
    int deleteById(Long userId);
}
