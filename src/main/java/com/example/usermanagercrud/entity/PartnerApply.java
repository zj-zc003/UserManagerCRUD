package com.example.usermanagercrud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerApply {
    private Long id;
    private Long userId;
    private String realName;
    private String phone;
    private Integer status; // 0:待审核,1:通过,2:拒绝
    private Date applyTime;
    private Date auditTime;
}