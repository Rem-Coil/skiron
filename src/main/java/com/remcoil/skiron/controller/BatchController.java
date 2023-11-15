package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.batch.BatchFull;
import com.remcoil.skiron.service.BatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/batch")
    public List<BatchFull> getAll() {
        return batchService.getAll();
    }
}
