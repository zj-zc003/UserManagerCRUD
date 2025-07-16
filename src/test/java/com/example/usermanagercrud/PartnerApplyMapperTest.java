package com.example.usermanagercrud;

import static org.junit.jupiter.api.Assertions.*;

import com.example.usermanagercrud.entity.PartnerApply;
import com.example.usermanagercrud.mapper.PartnerApplyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class PartnerApplyMapperTest {

    @Autowired
    private PartnerApplyMapper applyMapper;

    // 初始化测试数据
    @BeforeEach
    public void setup() {
        // 使用前面提供的SQL初始化数据
        // 实际测试中可以用@Sql注解加载SQL文件
    }

    // ================ 查询测试 ================
    
    @Test
    public void testSelectById() {
        // 测试存在的ID
        PartnerApply apply = applyMapper.selectById(1L);
        assertNotNull(apply);
        assertEquals("张三", apply.getRealName());
        assertEquals(0, apply.getStatus());
        assertNull(apply.getAuditTime());
        
        // 测试不存在的ID
        PartnerApply notExist = applyMapper.selectById(999L);
        assertNull(notExist);
    }

    @Test
    public void testSelectList_NoParams() {
        List<PartnerApply> list = applyMapper.selectList(null, null);
        assertEquals(5, list.size());
        // 验证排序：apply_time DESC
        assertTrue(list.get(0).getApplyTime().after(list.get(1).getApplyTime()));
    }

    @Test
    public void testSelectList_ByName() {
        List<PartnerApply> list = applyMapper.selectList("张", null);
        assertEquals(1, list.size());
        assertEquals("张三", list.get(0).getRealName());
        
        // 测试模糊匹配
        List<PartnerApply> list2 = applyMapper.selectList("四", null);
        assertEquals(1, list2.size());
        assertEquals("李四", list2.get(0).getRealName());
    }

    @Test
    public void testSelectList_ByStatus() {
        // 状态0：待审核
        List<PartnerApply> pendingList = applyMapper.selectList(null, 0);
        assertEquals(2, pendingList.size());
        assertTrue(pendingList.stream().allMatch(a -> a.getStatus() == 0));
        
        // 状态1：已通过
        List<PartnerApply> approvedList = applyMapper.selectList(null, 1);
        assertEquals(2, approvedList.size());
        assertTrue(approvedList.stream().allMatch(a -> a.getStatus() == 1));
    }

    @Test
    public void testSelectList_Combined() {
        List<PartnerApply> list = applyMapper.selectList("六", 0);
        assertEquals(1, list.size());
        assertEquals("赵六", list.get(0).getRealName());
        assertEquals(0, list.get(0).getStatus());
    }

    // ================ 更新测试 ================
    
    @Test
    public void testUpdate_Full() {
        // 获取原始数据
        PartnerApply original = applyMapper.selectById(1L);
        
        // 创建更新对象
        PartnerApply update = new PartnerApply();
        update.setId(1L);
        update.setRealName("张三丰");
        update.setPhone("13900000000");
        update.setStatus(1);
        update.setAuditTime(new Date());
        
        // 执行更新
        int rows = applyMapper.update(update);
        assertEquals(1, rows);
        
        // 验证更新结果
        PartnerApply updated = applyMapper.selectById(1L);
        assertEquals("张三丰", updated.getRealName());
        assertEquals("13900000000", updated.getPhone());
        assertEquals(1, updated.getStatus());
        assertNotNull(updated.getAuditTime());
        // 确保未修改字段保持原值
        assertEquals(original.getUserId(), updated.getUserId());
        assertEquals(original.getApplyTime(), updated.getApplyTime());
    }

    @Test
    public void testUpdate_Partial() {
        // 创建部分更新对象
        PartnerApply update = new PartnerApply();
        update.setId(1L);
        update.setStatus(1);
        update.setAuditTime(new Date());
        
        // 执行更新
        int rows = applyMapper.update(update);
        assertEquals(1, rows);
        
        // 验证更新结果
        PartnerApply updated = applyMapper.selectById(1L);
        assertEquals(1, updated.getStatus());
        assertNotNull(updated.getAuditTime());
        // 确保其他字段未改变
        assertEquals("张三", updated.getRealName());
        assertEquals("13800000001", updated.getPhone());
    }

    @Test
    public void testUpdate_NonExisting() {
        PartnerApply update = new PartnerApply();
        update.setId(999L);
        update.setStatus(1);
        
        int rows = applyMapper.update(update);
        assertEquals(0, rows); // 应返回0行更新
    }

    // ================ 插入测试 ================
    // 需要先添加insert方法
    
    @Test
    public void testInsert() {
        PartnerApply newApply = new PartnerApply();
        newApply.setUserId(1006L);
        newApply.setRealName("孙八");
        newApply.setPhone("13800000006");
        newApply.setStatus(0);
        newApply.setApplyTime(new Date());
        
        int rows = applyMapper.insert(newApply);
        assertEquals(1, rows);
        assertNotNull(newApply.getId()); // ID应被回填
        
        // 验证数据插入
        PartnerApply saved = applyMapper.selectById(newApply.getId());
        assertEquals("孙八", saved.getRealName());
        assertEquals(0, saved.getStatus());
    }

    // ================ 删除测试 ================
    // 需要先添加delete方法
    
    @Test
    public void testDelete() {
        int rows = applyMapper.deleteById(3L);
        assertEquals(1, rows);
        
        PartnerApply deleted = applyMapper.selectById(3L);
        assertNull(deleted);
    }
}
