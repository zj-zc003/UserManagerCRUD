package com.example.usermanagercrud.service;

import com.example.usermanagercrud.dto.TeacherInfo;

// TeacherService.java
public interface TeacherService {
    TeacherInfo getById(Long id);
    void validateTeacherExists(Long teacherId);
}