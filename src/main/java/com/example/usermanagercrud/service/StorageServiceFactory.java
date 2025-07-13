package com.example.usermanagercrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageServiceFactory {

    @Value("${storage.type:local}")
    private String storageType;
    
    @Autowired
    private LocalStorageService localStorageService;
    
    public StorageService getStorageService() {
        if ("local".equalsIgnoreCase(storageType)) {
            return localStorageService;
        }
        throw new IllegalArgumentException("不支持的存储类型: " + storageType);
    }
}
