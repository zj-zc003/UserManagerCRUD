package com.example.usermanagercrud.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

// TeacherInfo.java
@Data
@TableName("teacher_info")
public class TeacherInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotNull
    private Long userId;
    
    @NotBlank
    @Size(max = 50)
    private String realName;
    
    @Size(max = 100)
    private String title;
    
    private String intro;
    private String achievement;
    
    private JSONObject socialLinks; // 使用fastjson的JSONObject
    
    private Boolean isCertified;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
