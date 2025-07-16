package com.example.usermanagercrud.controller;

import com.example.usermanagercrud.entity.PartnerApply;
import com.example.usermanagercrud.service.PartnerApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// PartnerApplyController.java
@RestController
@RequestMapping("/partner/apply")
public class PartnerApplyController {

    @Autowired
    private PartnerApplyService applyService;

    // 分页获取申请列表
    @GetMapping
    public Map<String, Object> getApplies(
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {

        return applyService.getApplyList(searchName, status, pageNum, pageSize);
    }

    // 获取申请详情
    @GetMapping("/{id}")
    public PartnerApply getApplyDetail(@PathVariable Long id) {
        return applyService.getApplyDetail(id);
    }

    // 审核申请
    @PostMapping("/{id}/review")
    public ResponseEntity<?> reviewApply(
            @PathVariable Long id,
            @RequestParam boolean approved) {

        try {
            applyService.reviewApply(id, approved);
            String message = approved ? "申请已通过" : "申请已拒绝";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("审核失败: " + e.getMessage());
        }
    }
}