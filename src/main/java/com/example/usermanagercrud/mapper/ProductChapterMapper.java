package com.example.usermanagercrud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usermanagercrud.dto.ChapterDetailVO;
import com.example.usermanagercrud.entity.ProductChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductChapterMapper extends BaseMapper<ProductChapter> {
    
    @Select("SELECT c.*, m.file_name AS materialName, m.file_type AS materialType " +
            "FROM product_chapter c " +
            "LEFT JOIN material m ON c.material_id = m.id " +
            "WHERE c.product_id = #{productId} " +
            "ORDER BY c.sort ASC")
    List<ChapterDetailVO> getChaptersByProductId(@Param("productId") Long productId);
}