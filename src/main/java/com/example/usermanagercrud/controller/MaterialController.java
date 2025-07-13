package com.example.usermanagercrud.controller;


import com.example.usermanagercrud.dto.StorageResult;
import com.example.usermanagercrud.entity.Material;
import com.example.usermanagercrud.mapper.FileTypeMapper;
import com.example.usermanagercrud.mapper.MaterialMapper;
import com.example.usermanagercrud.service.StorageService;
import com.example.usermanagercrud.service.StorageServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final StorageServiceFactory storageServiceFactory;
    private final MaterialMapper materialMapper;
    private final FileTypeMapper fileTypeMapper;
    
    @Autowired
    public MaterialController(StorageServiceFactory storageServiceFactory, 
                              MaterialMapper materialMapper,
                              FileTypeMapper fileTypeMapper) {
        this.storageServiceFactory = storageServiceFactory;
        this.materialMapper = materialMapper;
        this.fileTypeMapper = fileTypeMapper;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMaterial(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "userId", required = false) Long userId) { // 通过参数传递用户ID
        
        try {
            // 基本验证
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("请选择要上传的文件");
            }
            
            // 获取存储服务
            StorageService storageService = storageServiceFactory.getStorageService();
            
            // 上传文件
            StorageResult result = storageService.upload(file);
            if (!result.isSuccess()) {
                return ResponseEntity.internalServerError().body(result.getMessage());
            }
            
            // 创建素材记录
            Material material = new Material();
            material.setTitle(title != null ? title : file.getOriginalFilename());
            material.setDescription(description);
            material.setFileName(file.getOriginalFilename());
            material.setFileKey(result.getFileKey());
            material.setFileSize(file.getSize());
            
            // 设置文件类型和格式
            String mimeType = file.getContentType();
            material.setFileType(fileTypeMapper.mapToFileType(mimeType));
            material.setFileFormat(fileTypeMapper.getFileFormat(file.getOriginalFilename()));
            
            // 设置其他字段
            material.setCategoryId(categoryId);
            material.setDownloadCount(0);
            material.setViewCount(0);
            material.setIsDeleted(false);
            
            // 用户和时间信息
            if (userId == null) {
                userId = 1L; // 默认用户ID
            }
            material.setCreatedBy(userId);
            
            LocalDateTime now = LocalDateTime.now();
            material.setCreatedAt(now);
            material.setUpdatedAt(now);
            
            // 保存到数据库
            materialMapper.insertMaterial(material);
            
            return ResponseEntity.ok(Map.of(
                "id", material.getId(),
                "title", material.getTitle(),
                "fileKey", result.getFileKey(),
                "fileType", material.getFileType(),
                "url", storageService.getUrl(result.getFileKey()),
                "createdAt", now
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("上传失败: " + e.getMessage());
        }
    }
}
