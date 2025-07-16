package com.example.usermanagercrud.service;// PartnerApplyService.java
import com.example.usermanagercrud.entity.PartnerApply;
import com.example.usermanagercrud.mapper.PartnerApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartnerApplyService {
    
    @Autowired
    private PartnerApplyMapper applyMapper;

    // 分页获取申请列表
    public Map<String, Object> getApplyList(String searchName, Integer status, int pageNum, int pageSize) {
        // 计算分页偏移量
        int offset = (pageNum - 1) * pageSize;

        // 查询数据
        List<PartnerApply> list = applyMapper.selectList(searchName, status, offset, pageSize);

        // 查询总数
        int total = applyMapper.countList(searchName, status);

        // 计算总页数
        int totalPages = (int) Math.ceil((double) total / pageSize);

        // 返回分页结果
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalPages", totalPages);

        return result;
    }
    
    // 获取申请详情
    public PartnerApply getApplyDetail(Long id) {
        return applyMapper.selectById(id);
    }
    
    // 审核申请（通过/拒绝）
    public void reviewApply(Long id, boolean approved) {
        PartnerApply apply = applyMapper.selectById(id);
        if (apply == null) {
            throw new RuntimeException("申请记录不存在");
        }

        // 只能审核待处理状态(0)的申请
        if (apply.getStatus() != 0) {
            throw new RuntimeException("只能审核待处理的申请");
        }

        PartnerApply update = new PartnerApply();
        update.setId(id);
        update.setStatus(approved ? 1 : 2); // 1=通过, 2=拒绝
        update.setAuditTime(new Date());

        int rows = applyMapper.update(update);
        if (rows == 0) {
            throw new RuntimeException("审核更新失败");
        }
    }

    // 获取状态文本
    private String getStatusText(int status) {
        switch (status) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已拒绝";
            default: return "未知状态";
        }
    }
}
