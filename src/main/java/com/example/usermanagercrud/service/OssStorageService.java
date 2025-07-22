package com.example.usermanagercrud.service;

import com.example.usermanagercrud.dto.StorageResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "oss")
public class OssStorageService implements StorageService {
    @Override
    public StorageResult upload(MultipartFile file) {
        return null;
    }

    @Override
    public boolean delete(String fileKey) {
        return false;
    }

    @Override
    public String getUrl(String fileKey) {
        return "";
    }

    @Override
    public String generatePresignedUrl(String fileKey) {
        return "";
    }
    // 实现OSS上传/访问逻辑（暂留接口）
}
