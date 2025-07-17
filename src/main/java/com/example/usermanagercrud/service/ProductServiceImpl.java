package com.example.usermanagercrud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.*;
import com.example.usermanagercrud.entity.KnowledgeProduct;
import com.example.usermanagercrud.entity.ProductChapter;
import com.example.usermanagercrud.entity.UserAccess;
import com.example.usermanagercrud.mapper.KnowledgeProductMapper;
import com.example.usermanagercrud.mapper.ProductChapterMapper;
import com.example.usermanagercrud.mapper.UserAccessMapper;
import com.example.usermanagercrud.model.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

// ProductServiceImpl.java
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final KnowledgeProductMapper productMapper;
    private final ProductChapterMapper chapterMapper;
    private final MaterialService materialService;
    private final TeacherService teacherService;
    private final UserAccessMapper userAccessMapper;

    @Override
    public Page<ProductListVO> getProductPage(ProductQueryDTO queryDTO) {
        Page<ProductListVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        
        QueryWrapper<ProductListVO> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(queryDTO.getTitle()), "p.title", queryDTO.getTitle());
        wrapper.eq(queryDTO.getStatus() != null, "p.status", queryDTO.getStatus());
        wrapper.eq(queryDTO.getType() != null, "p.type", queryDTO.getType());
        wrapper.eq(queryDTO.getTeacherId() != null, "p.teacher_id", queryDTO.getTeacherId());
        wrapper.orderBy(StringUtils.isNotBlank(queryDTO.getSortField()), 
                      "asc".equalsIgnoreCase(queryDTO.getSortOrder()), 
                      queryDTO.getSortField());
        
        return productMapper.selectProductPage(page, wrapper);
    }

    @Override
    public ProductDetailVO getProductDetail(Long id) {
        KnowledgeProduct product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("产品不存在");
        }
        
        ProductDetailVO detailVO = BeanUtil.copyProperties(product, ProductDetailVO.class);
        
        // 获取讲师信息
        TeacherInfo teacher = teacherService.getById(product.getTeacherId());
        detailVO.setTeacherName(teacher.getRealName());
        
        // 获取章节列表
        List<ChapterDetailVO> chapters = chapterMapper.getChaptersByProductId(id);
        detailVO.setChapters(chapters);
        
        return detailVO;
    }

    @Override
    @Transactional
    public Long createProduct(ProductCreateDTO dto) {
        // 验证讲师是否存在
        teacherService.validateTeacherExists(dto.getTeacherId());
        
        // 转换DTO到Entity
        KnowledgeProduct product = BeanUtil.copyProperties(dto, KnowledgeProduct.class);
        
        // 设置封面素材
        if (StringUtils.isNotBlank(dto.getCoverKey())) {
            materialService.validateMaterialExists(dto.getCoverKey());
            product.setCoverKey(dto.getCoverKey());
        }
        
        // 保存产品
        productMapper.insert(product);
        
        // 保存章节
        saveChapters(product.getId(), dto.getChapters());
        
        return product.getId();
    }
    
    private void saveChapters(Long productId, List<ChapterCreateDTO> chapters) {
        if (CollectionUtils.isEmpty(chapters)) return;
        
        int sort = 1;
        for (ChapterCreateDTO chapterDTO : chapters) {
            ProductChapter chapter = BeanUtil.copyProperties(chapterDTO, ProductChapter.class);
            chapter.setProductId(productId);
            chapter.setSort(sort++);
            
            // 验证关联素材
            if (chapterDTO.getMaterialId() != null) {
                materialService.validateMaterialExists(chapterDTO.getMaterialId());
            }
            
            chapterMapper.insert(chapter);
        }
    }

    @Override
    @Transactional
    public void updateProduct(ProductUpdateDTO dto) {
        KnowledgeProduct product = productMapper.selectById(dto.getId());
        if (product == null) {
            throw new BusinessException("产品不存在");
        }
        
        // 更新产品信息
        BeanUtil.copyProperties(dto, product, CopyOptions.create().ignoreNullValue());
        
        // 更新封面
        if (StringUtils.isNotBlank(dto.getCoverKey())) {
            materialService.validateMaterialExists(dto.getCoverKey());
            product.setCoverKey(dto.getCoverKey());
        }
        
        productMapper.updateById(product);
        
        // 更新章节 - 先删除旧章节再新增
        chapterMapper.delete(new QueryWrapper<ProductChapter>().eq("product_id", dto.getId()));
        saveChapters(dto.getId(), dto.getChapters());
    }

    @Override
    public void updateProductStatus(Long id, Integer status) {
        if (!Arrays.asList(0, 1, 2).contains(status)) {
            throw new BusinessException("无效的产品状态");
        }
        
        KnowledgeProduct product = new KnowledgeProduct();
        product.setId(id);
        product.setStatus(status);
        productMapper.updateById(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        // 检查是否有用户购买
        Long accessCount = userAccessMapper.selectCount(
            new QueryWrapper<UserAccess>().eq("product_id", id)).longValue();
        if (accessCount > 0) {
            throw new BusinessException("产品已有用户购买，无法删除");
        }
        
        // 删除产品
        productMapper.deleteById(id);
        
        // 删除关联章节
        chapterMapper.delete(new QueryWrapper<ProductChapter>().eq("product_id", id));
    }
}