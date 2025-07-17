package com.example.usermanagercrud.dto;

import lombok.Data;

// TeacherDetailVO.java
@Data
public class TeacherDetailVO extends TeacherInfo {
    private String avatar;
    private String nickname;
}