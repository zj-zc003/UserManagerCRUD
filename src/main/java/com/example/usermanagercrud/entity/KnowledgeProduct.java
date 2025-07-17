package com.example.usermanagercrud.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// KnowledgeProduct.java
@Data
@TableName("knowledge_product")
public class KnowledgeProduct {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotBlank(message = "产品标题不能为空")
    private String title;
    
    private String subtitle;
    
    @NotBlank(message = "封面图不能为空")
    private String coverKey;
    
    @Size(max = 1000, message = "描述不能超过1000字符")
    private String description;
    
    //@Lob
    private String detailHtml;
    
    @DecimalMin(value = "0.00", message = "价格不能小于0")
    private BigDecimal price;
    
    @DecimalMin(value = "0.00", message = "原价不能小于0")
    private BigDecimal originPrice;
    
    @NotNull(message = "状态不能为空")
    private Integer status; // 1:上架 0:下架 2:草稿
    
    @NotNull(message = "产品类型不能为空")
    private Integer type; // 1:专栏 2:视频课 3:音频课 4:电子书 5:直播
    
    private Integer studyMode; // 1:永久有效 2:有效期天数
    private Integer validDays;
    
    @NotNull(message = "讲师不能为空")
    private Long teacherId;
    
    private Long categoryId;
    private Boolean isRecommend;
    private Integer sort;
    
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}