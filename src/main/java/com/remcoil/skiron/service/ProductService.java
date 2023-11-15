package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.database.entity.Product;
import com.remcoil.skiron.database.repository.ProductRepository;
import com.remcoil.skiron.model.product.ProductFull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(Kit kit, List<Batch> batches) {
        List<Product> products = new ArrayList<>(kit.productQuantity());

        for (Batch batch : batches) {
            for (int i = 1; i <= kit.getBatchSize(); i++) {
                products.add(new Product(i, batch.getId()));
            }
        }

        productRepository.saveAll(products);
    }

    public List<ProductFull> getAll() {
        return productRepository.findAll().stream()
                .map(ProductFull::fromEntity)
                .collect(Collectors.toList());
    }
}
