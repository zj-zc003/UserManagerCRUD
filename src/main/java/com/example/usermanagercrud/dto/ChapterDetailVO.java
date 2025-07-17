package com.example.usermanagercrud.dto;

import lombok.Data;

@Data
public class ChapterDetailVO {
    private Long id;
    private String title;
    private Integer contentType;
    private Long materialId;
    private String materialName;
    private String materialType;
    private Integer duration;
    private Boolean isFree;
    private Long parentId;
    private Integer sort;
}