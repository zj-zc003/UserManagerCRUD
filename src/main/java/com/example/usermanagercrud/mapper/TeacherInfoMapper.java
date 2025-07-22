package com.example.usermanagercrud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usermanagercrud.dto.TeacherDetailVO;
import com.example.usermanagercrud.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// TeacherInfoMapper.java
@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {
    
    @Select("SELECT t.*, u.avatar, u.nickname " +
            "FROM teacher_info t " +
            "JOIN sys_user u ON t.user_id = u.user_id " +
            "WHERE t.id = #{id}")
    TeacherDetailVO getDetailById(@Param("id") Long id);

    @Select("SELECT t.*, u.avatar, u.nickname " +
            "FROM teacher_info t " +
            "JOIN sys_user u ON t.user_id = u.user_id "
            )
    List<TeacherDetailVO> getDetail();
}