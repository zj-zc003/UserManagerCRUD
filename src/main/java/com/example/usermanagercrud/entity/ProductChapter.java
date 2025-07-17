package com.example.usermanagercrud.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

// ProductChapter.java
@Data
@TableName("product_chapter")
public class ProductChapter {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotNull(message = "产品ID不能为空")
    private Long productId;
    
    @NotBlank(message = "章节标题不能为空")
    private String title;
    
    @NotNull(message = "内容类型不能为空")
    private Integer contentType; // 1:视频 2:音频 3:图文 4:PDF 5:测验
    
    private Long materialId;
    private Integer duration;
    
    @NotNull(message = "免费状态不能为空")
    private Boolean isFree;
    
    private Long parentId;
    private Integer sort;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}