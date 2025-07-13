package com.example.usermanagercrud.mapper;

import com.example.usermanagercrud.entity.MaterialCategory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MaterialCategoryMapper {

    @Insert("INSERT INTO material_category (name, parent_id, sort_order, is_system, created_by) " +
            "VALUES (#{name}, #{parentId}, #{sortOrder}, #{isSystem}, #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCategory(MaterialCategory category);

    @Update("UPDATE material_category SET " +
            "name = #{name}, " +
            "parent_id = #{parentId}, " +
            "sort_order = #{sortOrder} " +
            "WHERE id = #{id}")
    int updateCategory(MaterialCategory category);

    @Update("UPDATE material_category SET sort_order = #{sortOrder} WHERE id = #{id}")
    int updateCategoryOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    @Delete("DELETE FROM material_category WHERE id = #{id} AND is_system = 0")
    int deleteCategory(Long id);

    @Select("SELECT * FROM material_category WHERE id = #{id}")
    @Results(id = "categoryMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "sortOrder", column = "sort_order"),
            @Result(property = "isSystem", column = "is_system"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    MaterialCategory selectById(Long id);

    @Select("SELECT * FROM material_category ORDER BY sort_order ASC, id ASC")
    @ResultMap("categoryMap")
    List<MaterialCategory> selectAllCategories();

    @Select("SELECT * FROM material_category WHERE parent_id = #{parentId} ORDER BY sort_order ASC, id ASC")
    @ResultMap("categoryMap")
    List<MaterialCategory> selectByParentId(@Param("parentId") Long parentId);

    @Select("SELECT COUNT(*) FROM material_category WHERE parent_id = #{parentId}")
    int countByParentId(@Param("parentId") Long parentId);

    @Select("SELECT COUNT(*) FROM material WHERE category_id = #{categoryId}")
    int countMaterialsByCategory(@Param("categoryId") Long categoryId);
}
