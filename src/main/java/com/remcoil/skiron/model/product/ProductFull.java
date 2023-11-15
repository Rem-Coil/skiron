package com.remcoil.skiron.model.product;

import com.remcoil.skiron.database.entity.Product;

public record ProductFull(
        long id,
        int productNumber,
        boolean active,
        boolean locked,
        long batchId
) {
    public static ProductFull fromEntity(Product product) {
        return new ProductFull(
                product.getId(),
                product.getProductNumber(),
                product.getActive(),
                product.getLocked(),
                product.getBatchId()
        );
    }
}
