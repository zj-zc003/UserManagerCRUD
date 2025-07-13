package com.example.usermanagercrud.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MaterialCategory {
    private Long id;
    private String name;
    private Long parentId = 0L; // 默认值
    private Integer sortOrder = 0; // 默认值
    private Boolean isSystem = false; // 默认值
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 为布尔字段添加额外的 getter
    public boolean isSystem() {
        return isSystem != null && isSystem;
    }
}
