package com.example.usermanagercrud.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductListVO {
    private Long id;
    private String title;
    private String coverKey;
    private BigDecimal price;
    private BigDecimal originPrice;
    private Integer status;
    private Integer type;
    private String teacherName;
    private LocalDateTime createTime;
}