package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.Product;
import com.remcoil.skiron.database.repository.ProductRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.product.ProductFull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductFull> getAll() {
        return productRepository.findAll().stream()
                .map(ProductFull::fromEntity)
                .toList();
    }

    public List<ProductFull> getByBatchId(long id) {
        return productRepository.findAllByBatchId(id).stream()
                .map(ProductFull::fromEntity)
                .toList();
    }

    public void deactivate(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntryDoesNotExistException("Not Found"));
        productRepository.save(product.deactivated());
        productRepository.save(product.getNew());
    }

    public ProductFull getById(long id) {
        return ProductFull.fromEntity(productRepository.findById(id)
                .orElseThrow(() -> new EntryDoesNotExistException("Not Found")));
    }

    protected void create(KitFull kit, List<Batch> batches) {
        List<Product> products = new ArrayList<>();
        for (Batch batch : batches) {
            for (int i = 1; i <= kit.batchSize(); i++) {
                products.add(new Product(i, batch.getId()));
            }
        }
        productRepository.saveAll(products);
    }

    protected void createExtra(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        List<Product> products = new ArrayList<>();
        for (Batch batch : batches) {
            for (int i = oldKit.batchSize() + 1; i <= newKit.batchSize(); i++) {
                products.add(new Product(i, batch.getId()));
            }
        }
        productRepository.saveAll(products);
    }

    protected void deleteExtra(List<Batch> batches, int ceilingNumber) {
        productRepository.deleteWithHigherNumber(
                ceilingNumber,
                batches.stream()
                        .map(Batch::getId)
                        .toList()
        );
    }

    protected void deleteInactiveByBatchId(long id) {
        productRepository.deleteInactiveByBatch(id);
    }

    protected void setLockStatusById(long id, boolean status) {
        productRepository.setLockStatus(id, status);
    }
}
