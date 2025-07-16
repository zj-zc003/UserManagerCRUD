package com.example.usermanagercrud.mapper;

import com.example.usermanagercrud.entity.PartnerApply;
import org.apache.ibatis.annotations.*;

import java.util.List;

// PartnerApplyMapper.java（使用SelectProvider）
@Mapper
public interface PartnerApplyMapper {

    // 分页查询申请列表（添加分页参数）
    @SelectProvider(type = PartnerApplySqlProvider.class, method = "buildSelectSql")
    List<PartnerApply> selectList(
            @Param("realName") String realName,
            @Param("status") Integer status,
            @Param("offset") int offset,      // 新增偏移量参数
            @Param("pageSize") int pageSize   // 新增页大小参数
    );

    /*@SelectProvider(type = PartnerApplySqlProvider.class, method = "buildSelectSql_0")
    List<PartnerApply> selectList(
        @Param("realName") String realName,
        @Param("status") Integer status
    );*/
    // 新增：分页计数查询
    @SelectProvider(type = PartnerApplySqlProvider.class, method = "buildCountSql")
    int countList(
            @Param("realName") String realName,
            @Param("status") Integer status
    );


    // 根据ID查询申请
    @Select("SELECT * FROM partner_apply WHERE id = #{id}")
    PartnerApply selectById(@Param("id") Long id);

    // 更新申请记录
    @UpdateProvider(type = PartnerApplySqlProvider.class, method = "buildUpdateSql")
    int update(PartnerApply apply);

    // 插入新记录
    @Insert("INSERT INTO partner_apply(user_id, real_name, phone, status, apply_time) " +
            "VALUES(#{userId}, #{realName}, #{phone}, #{status}, #{applyTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PartnerApply apply);

    // 删除记录
    @Delete("DELETE FROM partner_apply WHERE id = #{id}")
    int deleteById(Long id);
}
