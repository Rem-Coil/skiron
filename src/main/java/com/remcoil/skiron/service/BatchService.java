package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.repository.BatchRepository;
import com.remcoil.skiron.model.batch.BatchFull;
import com.remcoil.skiron.model.kit.KitFull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProductService productService;

    public BatchService(BatchRepository batchRepository, ProductService productService) {
        this.batchRepository = batchRepository;
        this.productService = productService;
    }

    public List<BatchFull> getAll() {
        return batchRepository.findAll().stream()
                .map(BatchFull::fromEntity)
                .toList();
    }

    public List<Batch> getByKitId(Long id) {
        return batchRepository.findByKitId(id);
    }

    public void create(KitFull kit, int startNumber) {
        List<Batch> batches = new ArrayList<>();
        for (int i = startNumber; i <= kit.batchesQuantity(); i++) {
            batches.add(new Batch(i, kit.id()));
        }

        batches = batchRepository.saveAll(batches);
        productService.create(kit, batches);
    }

    public void resizeBatches(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        if (oldKit.batchSize() > newKit.batchSize()) {
            productService.deleteExtra(batches, newKit.batchSize());
        } else if (oldKit.batchSize() < newKit.batchSize()) {
            productService.createExtra(oldKit, newKit, batches);
        }
    }

    public void updateBatchesQuantity(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        if (oldKit.batchesQuantity() > newKit.batchesQuantity()) {
            int deleteNumber = oldKit.batchesQuantity() - newKit.batchesQuantity();
            for (int i = 1; i <= deleteNumber; i++) {
                batchRepository.deleteById(batches.get(batches.size() - i).getId());
            }
        } else if (oldKit.batchesQuantity() < newKit.batchesQuantity()) {
            create(newKit, oldKit.batchesQuantity() + 1);
        }
    }
}
