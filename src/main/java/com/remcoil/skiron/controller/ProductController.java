package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.product.ProductFull;
import com.remcoil.skiron.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public List<ProductFull> getAll() {
        return productService.getAll();
    }
}
