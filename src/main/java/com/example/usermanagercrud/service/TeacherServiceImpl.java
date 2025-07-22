package com.example.usermanagercrud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.TeacherCreateDTO;
import com.example.usermanagercrud.dto.TeacherDetailVO;
import com.example.usermanagercrud.dto.TeacherQueryDTO;
import com.example.usermanagercrud.dto.TeacherUpdateDTO;
import com.example.usermanagercrud.entity.TeacherInfo;
import com.example.usermanagercrud.mapper.TeacherInfoMapper;
import com.example.usermanagercrud.model.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    /**
     * 创建讲师
     */
    @Transactional
    @Override
    public Long createTeacher(TeacherCreateDTO dto) {
        // 检查用户是否已存在讲师信息
        LambdaQueryWrapper<TeacherInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherInfo::getUserId, dto.getUserId())
                .last("LIMIT 1");

        if (teacherMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该用户已存在讲师信息");
        }

        TeacherInfo teacher = new TeacherInfo();
        BeanUtils.copyProperties(dto, teacher);
        teacher.setCreateTime(LocalDateTime.now());

        teacherMapper.insert(teacher);
        return teacher.getId();
    }

    /**
     * 更新讲师信息
     */
    @Transactional
    @Override
    public void updateTeacher(TeacherUpdateDTO dto) {
        TeacherInfo teacher = teacherMapper.selectById(dto.getId());
        if (teacher == null) {
            throw new BusinessException("讲师不存在");
        }

        BeanUtils.copyProperties(dto, teacher);

        int result = teacherMapper.updateById(teacher);
        if (result == 0) {
            throw new BusinessException("更新讲师信息失败，请重试");
        }
    }

    /**
     * 获取讲师详情
     */
    @Override
    public TeacherInfo getTeacherById(Long id) {
        TeacherInfo teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException("讲师不存在");
        }
        return teacher;
    }

    /**
     * 删除讲师（逻辑删除）
     */
    @Transactional
    @Override
    public void deleteTeacher(Long id) {
        TeacherInfo teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new BusinessException("讲师不存在");
        }

        teacherMapper.updateById(teacher);
    }

    /**
     * 分页查询讲师列表
     */
    @Override
    public Page<TeacherInfo> listTeachers(TeacherQueryDTO queryDTO) {
        LambdaQueryWrapper<TeacherInfo> wrapper = new LambdaQueryWrapper<>();

        // 查询条件
        wrapper // 只查询未删除的
                .like(Objects.nonNull(queryDTO.getRealName()),
                        TeacherInfo::getRealName, queryDTO.getRealName())
                .like(Objects.nonNull(queryDTO.getTitle()),
                        TeacherInfo::getTitle, queryDTO.getTitle())
                .eq(Objects.nonNull(queryDTO.getIsCertified()),
                        TeacherInfo::getIsCertified, queryDTO.getIsCertified())
                .between(Objects.nonNull(queryDTO.getStartCreateTime())
                                && Objects.nonNull(queryDTO.getEndCreateTime()),
                        TeacherInfo::getCreateTime,
                        queryDTO.getStartCreateTime(), queryDTO.getEndCreateTime());

        // 分页参数
        Page<TeacherInfo> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());

        return teacherMapper.selectPage(page, wrapper);
    }

    /**
     * 根据用户ID获取讲师信息
     */
    @Override
    public TeacherInfo getTeacherByUserId(Long userId) {
        LambdaQueryWrapper<TeacherInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeacherInfo::getUserId, userId)
                .last("LIMIT 1");

        return teacherMapper.selectOne(wrapper);
    }

    @Override
    public List<TeacherDetailVO> getTeacherDetail() {
        return teacherMapper.getDetail();
    }
}