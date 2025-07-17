package com.example.usermanagercrud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usermanagercrud.dto.TeacherInfo;
import com.example.usermanagercrud.mapper.TeacherInfoMapper;
import com.example.usermanagercrud.model.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TeacherServiceImpl.java
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherInfoMapper teacherMapper;

    @Override
    public TeacherInfo getById(Long id) {
        TeacherInfo teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException("讲师不存在");
        }
        return teacher;
    }

    @Override
    public void validateTeacherExists(Long teacherId) {
        if (teacherId == null) {
            throw new BusinessException("讲师ID不能为空");
        }
        int count = teacherMapper.selectCount(new QueryWrapper<TeacherInfo>()
                .eq("id", teacherId)).intValue();
        if (count == 0) {
            throw new BusinessException("讲师不存在");
        }
    }
}