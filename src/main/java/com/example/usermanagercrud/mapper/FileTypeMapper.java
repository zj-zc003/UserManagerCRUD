package com.example.usermanagercrud.mapper;

import org.springframework.stereotype.Component;

@Component
public class FileTypeMapper {
    
    public String mapToFileType(String mimeType) {
        if (mimeType == null) return "other";
        
        if (mimeType.startsWith("image/")) {
            return "image";
        } else if (mimeType.startsWith("video/")) {
            return "video";
        } else if (mimeType.startsWith("audio/")) {
            return "audio";
        } else if (mimeType.equals("application/pdf") || 
                   mimeType.equals("application/msword") || 
                   mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                   mimeType.startsWith("text/")) {
            return "document";
        } else if (mimeType.equals("application/zip") || 
                   mimeType.equals("application/x-rar-compressed")) {
            return "archive";
        }
        return "other";
    }
    
    public String getFileFormat(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }
}
