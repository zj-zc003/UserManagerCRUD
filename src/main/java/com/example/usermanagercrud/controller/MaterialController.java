package com.example.usermanagercrud.controller;


import com.example.usermanagercrud.dto.MaterialDTO;
import com.example.usermanagercrud.dto.StorageResult;
import com.example.usermanagercrud.entity.Material;
import com.example.usermanagercrud.mapper.FileTypeMapper;
import com.example.usermanagercrud.mapper.MaterialMapper;
import com.example.usermanagercrud.service.MaterialService;
import com.example.usermanagercrud.service.StorageService;
import com.example.usermanagercrud.service.StorageServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final StorageServiceFactory storageServiceFactory;
    private final MaterialMapper materialMapper;
    private final FileTypeMapper fileTypeMapper;
    private final MaterialService materialService;

    @Autowired
    public MaterialController(StorageServiceFactory storageServiceFactory, MaterialMapper materialMapper, FileTypeMapper fileTypeMapper, MaterialService materialService) {
        this.storageServiceFactory = storageServiceFactory;
        this.materialMapper = materialMapper;
        this.fileTypeMapper = fileTypeMapper;
        this.materialService = materialService;
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

    // 获取素材列表
    @GetMapping
    public ResponseEntity<List<MaterialDTO>> getMaterials(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String fileType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        List<Material> materials = materialService.getFilteredMaterials(
                search, fileType, categoryId, sortBy, sortOrder
        );

        List<MaterialDTO> dtos = materials.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // 下载素材
    @PostMapping("/{id}/download")
    public ResponseEntity<?> recordDownload(@PathVariable Long id) {
        materialService.incrementDownloadCount(id);
        return ResponseEntity.ok().build();
    }

    // 获取素材详情
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> getMaterialDetails(@PathVariable Long id) {
        Material material = materialService.getMaterialById(id);
        materialService.incrementViewCount(id);
        return ResponseEntity.ok(convertToDTO(material));
    }

    // 删除素材
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
        materialService.softDeleteMaterial(id);
        return ResponseEntity.ok().build();
    }

    // 实体转DTO
    private MaterialDTO convertToDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setTitle(material.getTitle());
        dto.setDescription(material.getDescription());
        dto.setFileName(material.getFileName());
        dto.setFileKey(material.getFileKey());
        dto.setFileSize(material.getFileSize());
        dto.setFileType(material.getFileType());
        dto.setFileFormat(material.getFileFormat());
        dto.setCategoryId(material.getCategoryId());
        dto.setDownloadCount(material.getDownloadCount());
        dto.setViewCount(material.getViewCount());
        dto.setCreatedAt(material.getCreatedAt());
        dto.setUpdatedAt(material.getUpdatedAt());

        // 获取文件访问URL
        StorageService storageService = storageServiceFactory.getStorageService();
        dto.setFileUrl(storageService.getUrl(material.getFileKey()));
        // 获取原始文件路径
        String originalPath = dto.getFileUrl();
        // 转换路径为完整 HTTP URL
        if (originalPath != null && !originalPath.isEmpty()) {
            // 标准化路径：将反斜杠替换为正斜杠，去除开头的 "./" 或 ".\"
            String normalizedPath = originalPath
                    .replace("\\", "/")
                    .replaceAll("^\\./", "");

            // 构建完整 URL
            String fullUrl = "http://localhost:8080/" + normalizedPath;
            dto.setFileUrl2(fullUrl);
        } else {
            dto.setFileUrl2(""); // 或使用默认值
        }

        return dto;
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<?> recordView(@PathVariable Long id) {
        materialService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }

}
