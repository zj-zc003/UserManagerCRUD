package com.example.usermanagercrud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.*;

public interface ProductService {
    Page<ProductListVO> getProductPage(ProductQueryDTO queryDTO);
    ProductDetailVO getProductDetail(Long id);
    Long createProduct(ProductCreateDTO dto);
    void updateProduct(ProductUpdateDTO dto);
    void updateProductStatus(Long id, Integer status);
    void deleteProduct(Long id);
}