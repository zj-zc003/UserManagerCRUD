package com.example.usermanagercrud.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialDTO {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private String fileKey;
    private Long fileSize;
    private String fileType;
    private String fileFormat;
    private Long categoryId;
    private Integer downloadCount;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String fileUrl; // 文件访问URL
    private String fileUrl2; // 文件访问URL

    // Getters and Setters
}
