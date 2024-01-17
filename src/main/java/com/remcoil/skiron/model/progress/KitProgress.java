package com.remcoil.skiron.model.progress;

import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.model.operation.OperationTypeFull;

import java.util.List;

public record KitProgress(
        long id,
        String kitNumber,
        int acceptancePercentage,
        int batchesQuantity,
        List<OperationTypeFull> operationTypes,
        List<BatchProgress> batchesProgress
) {
    public KitProgress(Kit kit, List<OperationTypeFull> operationTypes, List<BatchProgress> batchesProgress) {
        this(
                kit.getId(),
                kit.getKitNumber(),
                kit.getAcceptancePercentage(),
                kit.getBatchesQuantity(),
                operationTypes,
                batchesProgress
        );
    }
}
