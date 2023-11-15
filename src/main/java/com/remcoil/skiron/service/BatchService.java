package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.database.repository.BatchRepository;
import com.remcoil.skiron.model.batch.BatchFull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProductService productService;

    public BatchService(BatchRepository batchRepository, ProductService productService) {
        this.batchRepository = batchRepository;
        this.productService = productService;
    }

    public void create(Kit kit) {
        List<Batch> batches = new ArrayList<>(kit.getBatchesQuantity());
        for (int i = 1; i <= kit.getBatchesQuantity(); i++) {
            batches.add(new Batch(i, kit.getId()));
        }

        batches = batchRepository.saveAll(batches);
        productService.create(kit, batches);
    }

    public List<BatchFull> getAll() {
        return batchRepository.findAll().stream()
                .map(BatchFull::fromEntity)
                .collect(Collectors.toList());
    }
}
