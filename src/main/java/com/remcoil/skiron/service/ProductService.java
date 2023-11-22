package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.Product;
import com.remcoil.skiron.database.repository.ProductRepository;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.product.ProductFull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductFull> getAll() {
        return productRepository.findAll().stream()
                .map(ProductFull::fromEntity)
                .toList();
    }

    public void create(KitFull kit, List<Batch> batches) {
        List<Product> products = new ArrayList<>();
        for (Batch batch : batches) {
            for (int i = 1; i <= kit.batchSize(); i++) {
                products.add(new Product(i, batch.getId()));
            }
        }
        productRepository.saveAll(products);
    }

    public void createExtra(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        List<Product> products = new ArrayList<>();
        for (Batch batch : batches) {
            for (int i = oldKit.batchSize() + 1; i <= newKit.batchSize(); i++) {
                products.add(new Product(i, batch.getId()));
            }
        }
        productRepository.saveAll(products);
    }

    public void deleteExtra(List<Batch> batches, int ceilingNumber) {
        productRepository.deleteWithHigherNumber(
                ceilingNumber,
                batches.stream()
                        .map(Batch::getId)
                        .toList()
        );
    }
}
