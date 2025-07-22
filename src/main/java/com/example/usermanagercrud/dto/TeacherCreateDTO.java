package com.example.usermanagercrud.dto;

import com.alibaba.fastjson.JSONObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherCreateDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;
    
    @Size(max = 100, message = "职称长度不能超过100个字符")
    private String title;
    
    private String intro;
    private String achievement;
    
    private JSONObject socialLinks;
    
    @NotNull(message = "认证状态不能为空")
    private Boolean isCertified;
}



