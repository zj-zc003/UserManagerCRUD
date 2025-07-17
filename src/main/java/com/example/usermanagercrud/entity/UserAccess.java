package com.example.usermanagercrud.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

// UserAccess.java
@Data
@TableName("user_access")
public class UserAccess {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long productId;
    
    @NotNull
    private Long orderId;
    
    private Integer accessStatus; // 1:有效 0:过期 2:冻结
    
    private LocalDateTime startTime;
    private LocalDateTime expireTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
