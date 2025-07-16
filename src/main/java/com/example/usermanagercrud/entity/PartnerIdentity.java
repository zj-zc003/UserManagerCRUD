package com.example.usermanagercrud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerIdentity {
    private Long id;
    private Long userId;
    private Integer level; // 1:普通,2:高级
    private Integer quota;
    private Date createTime;
}