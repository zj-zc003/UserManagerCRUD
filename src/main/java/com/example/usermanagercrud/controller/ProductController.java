package com.example.usermanagercrud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usermanagercrud.dto.*;
import com.example.usermanagercrud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// ProductController.java
@RestController
@RequestMapping("/api/admin/products")

@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "分页查询产品列表")
    public R<Page<ProductListVO>> listProducts(ProductQueryDTO queryDTO) {
        return R.success(productService.getProductPage(queryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取产品详情")
    public R<ProductDetailVO> getProductDetail(@PathVariable Long id) {
        return R.success(productService.getProductDetail(id));
    }

    @PostMapping
    @Operation(summary = "创建新产品")
    public R<Long> createProduct(@Valid @RequestBody ProductCreateDTO dto) {
        return R.success(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新产品信息")
    public R<Void> updateProduct(@PathVariable Long id, 
                                @Valid @RequestBody ProductUpdateDTO dto) {
        dto.setId(id);
        productService.updateProduct(dto);
        return R.success();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新产品状态")
    public R<Void> updateProductStatus(@PathVariable Long id, 
                                      @RequestParam Integer status) {
        productService.updateProductStatus(id, status);
        return R.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除产品")
    public R<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return R.success();
    }
}
