package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.repository.BatchRepository;
import com.remcoil.skiron.model.batch.BatchFull;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.product.ProductFull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProductService productService;
    private final ActionService actionService;
    private final ControlActionService controlActionService;
    private final AcceptanceActionService acceptanceActionService;

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

    @Transactional
    public void deleteHistory(long id) {
        List<Long> productsId = productService.getByBatchId(id).stream()
                .map(ProductFull::id)
                .toList();

        actionService.deleteByProductsId(productsId);
        controlActionService.deleteByProductsId(productsId);
        acceptanceActionService.deleteByProducts(productsId);
        productService.deleteInactiveByBatchId(id);
    }
}
