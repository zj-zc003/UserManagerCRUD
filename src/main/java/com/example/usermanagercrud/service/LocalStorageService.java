package com.example.usermanagercrud.service;


import com.example.usermanagercrud.dto.StorageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final String uploadDir;
    
    public LocalStorageService(@Value("${storage.local.path}") String uploadDir) {
        this.uploadDir = uploadDir;
        createUploadDir();
    }
    
    private void createUploadDir() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("无法创建上传目录", e);
        }
    }
    
    @Override
    public StorageResult upload(MultipartFile file) {
        try {
            // 验证文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                return StorageResult.builder()
                        .success(false)
                        .message("文件名无效")
                        .build();
            }
            
            // 生成唯一文件名
            String fileExtension = getFileExtension(originalFilename);
            String fileKey = UUID.randomUUID().toString() + fileExtension;
            Path filePath = Paths.get(uploadDir, fileKey);
            
            // 保存文件
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return StorageResult.builder()
                    .success(true)
                    .fileKey(fileKey)
                    .fullPath(filePath.toAbsolutePath().toString())
                    .build();
        } catch (IOException e) {
            return StorageResult.builder()
                    .success(false)
                    .message("文件上传失败: " + e.getMessage())
                    .build();
        }
    }
    
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }
    
    @Override
    public boolean delete(String fileKey) {
        try {
            Path filePath = Paths.get(uploadDir, fileKey);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public String getUrl(String fileKey) {
        // 在真实环境中这里应该返回可访问的URL
        // 目前只返回文件路径
        return Paths.get(uploadDir, fileKey).toString();
    }
}
