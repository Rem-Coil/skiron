package com.remcoil.skiron.model.kit;

import com.remcoil.skiron.database.entity.Kit;

public record KitFull(
        long id,
        String kitNumber,
        int batchesQuantity,
        int batchSize,
        int acceptancePercentage,
        long specificationId
) {
    public static KitFull fromEntity(Kit kit) {
        return new KitFull(
                kit.getId(),
                kit.getKitNumber(),
                kit.getBatchesQuantity(),
                kit.getBatchSize(),
                kit.getAcceptancePercentage(),
                kit.getSpecificationId()
        );
    }
}
