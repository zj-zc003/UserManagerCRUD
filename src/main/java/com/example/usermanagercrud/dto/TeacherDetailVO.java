package com.example.usermanagercrud.dto;

import com.example.usermanagercrud.entity.TeacherInfo;
import lombok.Data;

// TeacherDetailVO.java
@Data
public class TeacherDetailVO extends TeacherInfo {
    private String avatar;
    private String nickname;
}