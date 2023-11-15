package com.remcoil.skiron.model.batch;

import com.remcoil.skiron.database.entity.Batch;

public record BatchFull(
        long id,
        int batchNumber,
        long kitId
) {
    public static BatchFull fromEntity(Batch batch) {
        return new BatchFull(
                batch.getId(),
                batch.getBatchNumber(),
                batch.getKitId()
        );
    }
}
