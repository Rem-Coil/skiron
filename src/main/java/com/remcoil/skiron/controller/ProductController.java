package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.product.ProductFull;
import com.remcoil.skiron.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductFull> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductFull getById(@PathVariable("id") long id) {
        return productService.getById(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivate(@PathVariable long id) {
        productService.deactivate(id);
    }
}
