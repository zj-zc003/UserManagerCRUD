package com.example.usermanagercrud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.TeacherCreateDTO;
import com.example.usermanagercrud.dto.TeacherDetailVO;
import com.example.usermanagercrud.dto.TeacherQueryDTO;
import com.example.usermanagercrud.dto.TeacherUpdateDTO;
import com.example.usermanagercrud.entity.TeacherInfo;

import java.util.List;

// TeacherService.java
public interface TeacherService {
    TeacherInfo getById(Long id);
    void validateTeacherExists(Long teacherId);
    Long createTeacher(TeacherCreateDTO dto);
    void updateTeacher(TeacherUpdateDTO dto);
    TeacherInfo getTeacherById(Long id);
    void deleteTeacher(Long id);
    Page<TeacherInfo> listTeachers(TeacherQueryDTO queryDTO);
    TeacherInfo getTeacherByUserId(Long userId);
    List<TeacherDetailVO> getTeacherDetail();
}