package com.example.usermanagercrud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// ChapterCreateDTO.java
@Data
public class ChapterCreateDTO {
    @NotBlank(message = "章节标题不能为空")
    @Size(max = 100, message = "标题不能超过100字符")
    private String title;
    
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;
    
    private Long materialId;
    
    @Min(value = 0, message = "时长不能小于0")
    private Integer duration = 0;
    
    @NotNull(message = "免费状态不能为空")
    private Boolean isFree = false;
    
    private Long parentId;
}