package com.example.usermanagercrud.mapper;

import com.example.usermanagercrud.entity.PartnerApply;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// PartnerApplySqlProvider.java（SQL构建类）
public class PartnerApplySqlProvider {

    // 分页查询SQL（使用分页参数）
    public String buildSelectSql(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("partner_apply");
            if (params.get("realName") != null && !((String) params.get("realName")).isEmpty()) {
                WHERE("real_name LIKE CONCAT('%', #{realName}, '%')");
            }
            if (params.get("status") != null) {
                WHERE("status = #{status}");
            }
            ORDER_BY("apply_time DESC");
            // 使用传入的分页参数
            LIMIT("#{pageSize} OFFSET #{offset}");
        }}.toString();
    }

    /*// 分页计数SQL（不需要分页参数）
    public String buildCountSql(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("partner_apply");
            if (params.get("realName") != null && !((String) params.get("realName")).isEmpty()) {
                WHERE("real_name LIKE CONCAT('%', #{realName}, '%')");
            }
            if (params.get("status") != null) {
                WHERE("status = #{status}");
            }
        }}.toString();
    }*/

    // 构建更新SQL
    public String buildUpdateSql(PartnerApply apply) {
        return new SQL() {{
            UPDATE("partner_apply");
            if (apply.getRealName() != null) {
                SET("real_name = #{realName}");
            }
            if (apply.getPhone() != null) {
                SET("phone = #{phone}");
            }
            if (apply.getStatus() != null) {
                SET("status = #{status}");
            }
            if (apply.getAuditTime() != null) {
                SET("audit_time = #{auditTime}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    // 新增：分页计数SQL
    public String buildCountSql(Map<String, Object> params) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("partner_apply");
            if (params.get("realName") != null && !((String) params.get("realName")).isEmpty()) {
                WHERE("real_name LIKE CONCAT('%', #{realName}, '%')");
            }
            if (params.get("status") != null) {
                WHERE("status = #{status}");
            }
        }}.toString();
    }
}
