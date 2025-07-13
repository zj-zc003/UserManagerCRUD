package com.example.usermanagercrud.mapper;

import com.example.usermanagercrud.entity.Material;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MaterialMapper {

    @Insert("INSERT INTO material (title, description, file_name, file_key, file_size, file_type, file_format, " +
            "category_id, download_count, view_count, is_deleted, created_by, created_at, updated_at) " +
            "VALUES (#{title}, #{description}, #{fileName}, #{fileKey}, #{fileSize}, #{fileType}, #{fileFormat}, " +
            "#{categoryId}, #{downloadCount}, #{viewCount}, #{isDeleted}, #{createdBy}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMaterial(Material material);
}
