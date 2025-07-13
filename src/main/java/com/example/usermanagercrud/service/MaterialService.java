package com.example.usermanagercrud.service;

import com.example.usermanagercrud.entity.Material;
import com.example.usermanagercrud.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialService {

    private final MaterialMapper materialMapper;

    @Autowired
    public MaterialService(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public List<Material> getFilteredMaterials(String search, String fileType,
                                               Long categoryId, String sortBy,
                                               String sortOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", search);
        params.put("fileType", fileType);
        params.put("categoryId", categoryId);
        params.put("sortBy", sortBy);
        params.put("sortOrder", sortOrder);
        
        return materialMapper.selectMaterials(params);
    }

    public Material getMaterialById(Long id) {
        return materialMapper.selectById(id);
    }

    public void incrementDownloadCount(Long id) {
        materialMapper.incrementDownloadCount(id);
    }

    public void incrementViewCount(Long id) {
        materialMapper.incrementViewCount(id);
    }

    public void softDeleteMaterial(Long id) {
        materialMapper.softDelete(id);
    }
}
