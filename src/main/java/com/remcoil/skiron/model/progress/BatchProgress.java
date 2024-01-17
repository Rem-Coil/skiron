package com.remcoil.skiron.model.progress;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.ControlType;


import java.util.Map;

public record BatchProgress(
        long id,
        int batchNumber,
        Map<Long, Integer> operationProgress,
        Map<ControlType, Integer> controlProgress,
        Map<BatchStat, Integer> stats
) {
    public BatchProgress(Batch batch, Map<Long, Integer> operationProgress, Map<ControlType, Integer> controlProgress, Map<BatchStat, Integer> stats) {
        this(
                batch.getId(),
                batch.getBatchNumber(),
                operationProgress,
                controlProgress,
                stats
        );
    }
}
