package com.example.usermanagercrud.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// ProductCreateDTO.java
@Data
public class ProductCreateDTO {
    @NotBlank(message = "产品标题不能为空")
    @Size(max = 100, message = "标题不能超过100字符")
    private String title;
    
    @Size(max = 200, message = "副标题不能超过200字符")
    private String subtitle;
    
    @NotBlank(message = "封面图不能为空")
    private String coverKey;
    
    @Size(max = 1000, message = "描述不能超过1000字符")
    private String description;
    
    private String detailHtml;
    
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.00", message = "价格不能小于0")
    private BigDecimal price;
    
    @DecimalMin(value = "0.00", message = "原价不能小于0")
    private BigDecimal originPrice = BigDecimal.ZERO;
    
    @NotNull(message = "状态不能为空")
    private Integer status;
    
    @NotNull(message = "产品类型不能为空")
    private Integer type;
    
    private Integer studyMode;
    private Integer validDays;
    
    @NotNull(message = "讲师不能为空")
    private Long teacherId;
    
    private Long categoryId;
    private Long createUser;
    private String coverImg;
    private Boolean isRecommend = false;
    private Integer sort = 0;
    
    @Valid
    private List<ChapterCreateDTO> chapters;
}