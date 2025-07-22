package com.example.usermanagercrud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usermanagercrud.entity.Material;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MaterialMapper extends BaseMapper<Material> {

    @Insert("INSERT INTO material (title, description, file_name, file_key, file_size, file_type, file_format, " +
            "category_id, download_count, view_count, is_deleted, created_by, created_at, updated_at) " +
            "VALUES (#{title}, #{description}, #{fileName}, #{fileKey}, #{fileSize}, #{fileType}, #{fileFormat}, " +
            "#{categoryId}, #{downloadCount}, #{viewCount}, #{isDeleted}, #{createdBy}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMaterial(Material material);

    // 查询素材（带筛选和排序）
    @SelectProvider(type = MaterialSqlProvider.class, method = "selectMaterialsSql")
    List<Material> selectMaterials(Map<String, Object> params);


    // 根据ID查询素材
    @Select("SELECT * FROM material WHERE id = #{id} AND is_deleted = 0")
    Material selectById(Long id);

    // 增加下载计数
    @Update("UPDATE material SET download_count = download_count + 1 WHERE id = #{id}")
    int incrementDownloadCount(Long id);

    // 增加浏览计数
    @Update("UPDATE material SET view_count = view_count + 1 WHERE id = #{id}")
    int incrementViewCount(Long id);

    // 软删除素材
    @Update("UPDATE material SET is_deleted = 1 WHERE id = #{id}")
    int softDelete(Long id);

//    // 获取分类名称（如果需要）
//    @Select("SELECT name FROM category WHERE id = #{categoryId}")
//    String getCategoryName(Long categoryId);
}
