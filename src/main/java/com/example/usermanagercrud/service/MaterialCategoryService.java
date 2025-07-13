package com.example.usermanagercrud.service;

import com.example.usermanagercrud.dto.CategoryDTO;
import com.example.usermanagercrud.entity.MaterialCategory;
import com.example.usermanagercrud.mapper.MaterialCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaterialCategoryService {

    @Autowired
    private MaterialCategoryMapper categoryMapper;

    @Transactional
    public MaterialCategory createCategory(MaterialCategory category, Long userId) {
        // 设置默认值
        if (category.getParentId() == null) {
            category.setParentId(0L);
        }
        if (category.getSortOrder() == null) {
            // 为新分类设置排序值
            int count = categoryMapper.countByParentId(category.getParentId());
            category.setSortOrder(count + 1);
        }
        if (category.getIsSystem() == null) {
            category.setIsSystem(false);
        }

        category.setCreatedBy(userId);
        categoryMapper.insertCategory(category);
        return category;
    }

    @Transactional
    public MaterialCategory updateCategory(MaterialCategory category) {
        MaterialCategory existing = categoryMapper.selectById(category.getId());
        if (existing == null) {
            throw new RuntimeException("分类不存在");
        }
        if (existing.isSystem()) {
            throw new RuntimeException("系统分类不允许修改");
        }

        // 保留系统字段不变
        category.setIsSystem(existing.getIsSystem());
        category.setCreatedBy(existing.getCreatedBy());
        category.setCreatedAt(existing.getCreatedAt());

        categoryMapper.updateCategory(category);
        return category;
    }

    @Transactional
    public void deleteCategory(Long id) {
        MaterialCategory category = categoryMapper.selectById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        if (category.isSystem()) {
            throw new RuntimeException("系统分类不允许删除");
        }

        // 检查是否有子分类
        int childCount = categoryMapper.countByParentId(id);
        if (childCount > 0) {
            throw new RuntimeException("请先删除子分类");
        }

        // 检查分类下是否有素材
        int materialCount = categoryMapper.countMaterialsByCategory(id);
        if (materialCount > 0) {
            throw new RuntimeException("该分类下存在素材，无法删除");
        }

        categoryMapper.deleteCategory(id);
    }

    public List<CategoryDTO> getCategoryTree() {
        List<MaterialCategory> allCategories = categoryMapper.selectAllCategories();
        return buildCategoryTree(allCategories);
    }

    private List<CategoryDTO> buildCategoryTree(List<MaterialCategory> categories) {
        // 创建 ID 到 DTO 的映射
        Map<Long, CategoryDTO> dtoMap = new HashMap<>();
        Map<Long, List<MaterialCategory>> parentMap = new HashMap<>();

        // 初始化映射和分组
        for (MaterialCategory category : categories) {
            // 处理可能为空的字段
            Long parentId = Optional.ofNullable(category.getParentId()).orElse(0L);
            Integer sortOrder = Optional.ofNullable(category.getSortOrder()).orElse(0);

            dtoMap.put(category.getId(), convertToDTO(category));
            parentMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        // 构建树形结构
        List<CategoryDTO> rootCategories = new ArrayList<>();
        for (Map.Entry<Long, List<MaterialCategory>> entry : parentMap.entrySet()) {
            Long parentId = entry.getKey();
            List<MaterialCategory> children = entry.getValue();

            // 排序子分类
            children.sort(Comparator.comparingInt(c -> Optional.ofNullable(c.getSortOrder()).orElse(0)));

            if (parentId == 0 || !dtoMap.containsKey(parentId)) {
                // 顶级分类
                for (MaterialCategory child : children) {
                    rootCategories.add(dtoMap.get(child.getId()));
                }
            } else {
                // 子分类
                CategoryDTO parentDto = dtoMap.get(parentId);
                if (parentDto != null) {
                    for (MaterialCategory child : children) {
                        parentDto.addChild(dtoMap.get(child.getId()));
                    }
                }
            }
        }

        // 排序顶级分类
        rootCategories.sort(Comparator.comparingInt(CategoryDTO::getSortOrder));
        return rootCategories;
    }

    private CategoryDTO convertToDTO(MaterialCategory category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(Optional.ofNullable(category.getParentId()).orElse(0L));
        dto.setSortOrder(Optional.ofNullable(category.getSortOrder()).orElse(0));
        dto.setIsSystem(Optional.ofNullable(category.getIsSystem()).orElse(false));
        return dto;
    }

    @Transactional
    public void updateCategoryOrder(List<Long> categoryIds) {
        for (int i = 0; i < categoryIds.size(); i++) {
            categoryMapper.updateCategoryOrder(categoryIds.get(i), i + 1);
        }
    }
}
