package com.remcoil.skiron.model.kit;

import com.remcoil.skiron.database.entity.Kit;

public record KitRequest(
        String kitNumber,
        int batchesQuantity,
        int batchSize,
        int acceptancePercentage,
        long specificationId
) {
    public Kit toEntity() {
        return new Kit(
                kitNumber,
                batchesQuantity,
                batchSize,
                acceptancePercentage,
                specificationId
        );
    }
}
