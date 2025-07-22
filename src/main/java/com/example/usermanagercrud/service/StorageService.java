package com.example.usermanagercrud.service;

import com.example.usermanagercrud.dto.StorageResult;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    /**
     * 上传文件
     * @param file 上传的文件
     * @return 存储结果对象
     */
    StorageResult upload(MultipartFile file);
    
    /**
     * 删除文件
     * @param fileKey 文件唯一标识
     * @return 是否删除成功
     */
    boolean delete(String fileKey);
    
    /**
     * 获取文件访问URL
     * @param fileKey 文件唯一标识
     * @return 文件访问URL
     */
    String getUrl(String fileKey);
    // 增加OSS支持方法
    String generatePresignedUrl(String fileKey);
}
