package com.example.usermanagercrud.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class MaterialSqlProvider {
    public String selectMaterialsSql(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("material");
            WHERE("is_deleted = 0");
            
            if (params.get("search") != null && !params.get("search").equals("")) {
                WHERE("(title LIKE CONCAT('%', #{search}, '%') " +
                      "OR description LIKE CONCAT('%', #{search}, '%'))");
            }
            
            if (params.get("fileType") != null && !params.get("fileType").equals("")) {
                WHERE("file_type = #{fileType}");
            }
            
            if (params.get("categoryId") != null) {
                WHERE("category_id = #{categoryId}");
            }
            
            String sortBy = (String) params.getOrDefault("sortBy", "created_at");
            String sortOrder = (String) params.getOrDefault("sortOrder", "DESC");
            
            // 安全处理排序字段
            String orderField = "";
            switch (sortBy) {
                case "name": orderField = "title"; break;
                case "size": orderField = "file_size"; break;
                default: orderField = "created_at";
            }
            
            // 安全处理排序方向
            String direction = "DESC".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";
            
            ORDER_BY(orderField + " " + direction);
        }}.toString();
    }
}
