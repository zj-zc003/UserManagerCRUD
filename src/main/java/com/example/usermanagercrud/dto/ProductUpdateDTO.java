package com.example.usermanagercrud.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// ProductUpdateDTO.java
@Data
public class ProductUpdateDTO {
    @NotNull(message = "产品ID不能为空")
    private Long id;
    
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
    private BigDecimal originPrice;
    
    @NotNull(message = "产品类型不能为空")
    private Integer type;
    
    private Integer studyMode;
    private Integer validDays;
    
    @NotNull(message = "讲师不能为空")
    private Long teacherId;
    
    private Long categoryId;
    private Boolean isRecommend;
    private Integer sort;
    
    @Valid
    private List<ChapterCreateDTO> chapters;
}