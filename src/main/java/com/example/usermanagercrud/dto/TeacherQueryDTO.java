package com.example.usermanagercrud.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherQueryDTO {
    private String realName;
    private String title;
    private Boolean isCertified;
    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;
    private Integer page = 1;
    private Integer size = 10;
}
