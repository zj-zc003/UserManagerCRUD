package com.example.usermanagercrud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// ProductQueryDTO.java
@Data
public class ProductQueryDTO {
    @Schema(description = "页码", example = "1")
    private Integer page = 1;
    
    @Schema(description = "每页数量", example = "10")
    private Integer size = 10;
    
    @Schema(description = "产品标题")
    private String title;
    
    @Schema(description = "产品状态: 1-上架 0-下架 2-草稿")
    private Integer status;
    
    @Schema(description = "产品类型: 1-专栏 2-视频课 3-音频课 4-电子书 5-直播")
    private Integer type;
    
    @Schema(description = "讲师ID")
    private Long teacherId;
    
    @Schema(description = "排序字段")
    private String sortField;
    
    @Schema(description = "排序方向: asc/desc")
    private String sortOrder;
}






