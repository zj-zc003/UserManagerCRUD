package com.example.usermanagercrud.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailVO {
    private Long id;
    private String title;
    private String subtitle;
    private String coverKey;
    private String description;
    private String detailHtml;
    private BigDecimal price;
    private BigDecimal originPrice;
    private Integer status;
    private Integer type;
    private Integer studyMode;
    private Integer validDays;
    private Long teacherId;
    private String teacherName;
    private Long categoryId;
    private Boolean isRecommend;
    private Integer sort;
    private List<ChapterDetailVO> chapters;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}