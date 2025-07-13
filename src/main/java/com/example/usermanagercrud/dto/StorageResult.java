package com.example.usermanagercrud.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StorageResult {
    private boolean success;
    private String fileKey;
    private String fullPath;
    private String message;
}
