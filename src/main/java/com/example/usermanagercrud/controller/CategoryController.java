package com.example.usermanagercrud.controller;

import com.example.usermanagercrud.dto.CategoryDTO;
import com.example.usermanagercrud.entity.MaterialCategory;
import com.example.usermanagercrud.service.MaterialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private MaterialCategoryService categoryService;
    
    // 获取所有分类（树形结构）
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategoryTree() {
        List<CategoryDTO> categoryTree = categoryService.getCategoryTree();
        return ResponseEntity.ok(categoryTree);
    }
    
    // 创建分类
    @PostMapping
    public ResponseEntity<MaterialCategory> createCategory(@RequestBody MaterialCategory category,
                                                           @RequestAttribute Long userId) {
        category.setCreatedBy(userId);
        MaterialCategory created = categoryService.createCategory(category, userId);
        return ResponseEntity.ok(created);
    }
    
    // 更新分类
    @PutMapping("/{id}")
    public ResponseEntity<MaterialCategory> updateCategory(@PathVariable Long id, 
                                                          @RequestBody MaterialCategory category,
                                                          @RequestAttribute Long userId) {
        if (!id.equals(category.getId())) {
            throw new IllegalArgumentException("ID不匹配");
        }
        MaterialCategory updated = categoryService.updateCategory(category);
        return ResponseEntity.ok(updated);
    }
    
    // 删除分类
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    // 更新分类排序
    @PostMapping("/reorder")
    public ResponseEntity<Void> reorderCategories(@RequestBody List<Long> categoryIds) {
        categoryService.updateCategoryOrder(categoryIds);
        return ResponseEntity.ok().build();
    }
}
