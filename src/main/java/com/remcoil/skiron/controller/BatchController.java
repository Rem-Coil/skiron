package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.batch.BatchFull;
import com.remcoil.skiron.model.product.ProductFull;
import com.remcoil.skiron.service.BatchService;
import com.remcoil.skiron.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/batch")
public class BatchController {
    private final BatchService batchService;
    private final ProductService productService;

    public BatchController(BatchService batchService, ProductService productService) {
        this.batchService = batchService;
        this.productService = productService;
    }

    @GetMapping
    public List<BatchFull> getAll() {
        return batchService.getAll();
    }

    @GetMapping("/{id}/product")
    public List<ProductFull> getProductsByBatchId(@PathVariable("id") long id) {
        return productService.getByBatchId(id);
    }

    @DeleteMapping("{id}/history")
    public void deleteActionHistory(@PathVariable("id") long id) {
        batchService.deleteHistory(id);
    }
}
