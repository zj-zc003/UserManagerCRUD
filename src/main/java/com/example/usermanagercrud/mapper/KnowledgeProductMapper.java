package com.example.usermanagercrud.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.ProductListVO;
import com.example.usermanagercrud.entity.KnowledgeProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KnowledgeProductMapper extends BaseMapper<KnowledgeProduct> {

    @Select("SELECT p.*, u.real_name AS teacherName " +
            "FROM knowledge_product p " +
            "LEFT JOIN teacher_info t ON p.teacher_id = t.id " +
            "LEFT JOIN sys_user u ON t.user_id = u.user_id " +
            "${ew.customSqlSegment}")
    Page<ProductListVO> selectProductPage(Page<ProductListVO> page,
                                          @Param(Constants.WRAPPER) QueryWrapper<ProductListVO> wrapper);
}