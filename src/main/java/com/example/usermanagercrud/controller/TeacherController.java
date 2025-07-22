package com.example.usermanagercrud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.*;
import com.example.usermanagercrud.entity.TeacherInfo;
import com.example.usermanagercrud.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    
    private final TeacherService teacherService;
    
    /**
     * 创建讲师
     */
    @PostMapping
    public R<Long> createTeacher(@RequestBody TeacherCreateDTO dto) {
        Long id = teacherService.createTeacher(dto);
        return R.success(id);
    }
    
    /**
     * 更新讲师信息
     */
    @PutMapping
    public R<Void> updateTeacher(@RequestBody TeacherUpdateDTO dto) {
        teacherService.updateTeacher(dto);
        return R.success();
    }
    
    /**
     * 获取讲师详情
     */
    @GetMapping("/{id}")
    public R<TeacherInfo> getTeacher(@PathVariable Long id) {
        TeacherInfo teacher = teacherService.getTeacherById(id);
        return R.success(teacher);
    }
    
    /**
     * 删除讲师
     */
    @DeleteMapping("/{id}")
    public R<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return R.success();
    }
    
    /**
     * 分页查询讲师列表
     */
    @GetMapping("/list")
    public R<Page<TeacherInfo>> listTeachers(TeacherQueryDTO queryDTO) {
        Page<TeacherInfo> page = teacherService.listTeachers(queryDTO);
        return R.success(page);
    }

    @GetMapping("/listDetail")
    public R<List<TeacherDetailVO>> listTeachersDetail() {
        List<TeacherDetailVO> list = teacherService.getTeacherDetail();
        return R.success(list);
    }
    
    /**
     * 根据用户ID获取讲师信息
     */
    @GetMapping("/by-user/{userId}")
    public R<TeacherInfo> getTeacherByUserId(@PathVariable Long userId) {
        TeacherInfo teacher = teacherService.getTeacherByUserId(userId);
        return R.success(teacher);
    }
}
