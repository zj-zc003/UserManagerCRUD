package com.example.usermanagercrud.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
    private Integer sortOrder;
    private Boolean isSystem;
    private List<CategoryDTO> children = new ArrayList<>();

    // 辅助方法 - 添加子分类
    public void addChild(CategoryDTO child) {
        this.children.add(child);
        child.setParentName(this.name);
    }

    // 辅助方法 - 排序子分类
    public void sortChildren() {
        children.sort(Comparator.comparingInt(CategoryDTO::getSortOrder));
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Boolean getIsSystem() { return isSystem; }
    public void setIsSystem(Boolean isSystem) { this.isSystem = isSystem; }
    public List<CategoryDTO> getChildren() {
        // 返回排序后的子分类
        children.sort(Comparator.comparingInt(CategoryDTO::getSortOrder));
        return children;
    }
    public void setChildren(List<CategoryDTO> children) {
        this.children = children;
        if (children != null) {
            children.forEach(child -> child.setParentName(this.name));
        }
    }
}
