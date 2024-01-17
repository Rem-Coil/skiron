package com.remcoil.skiron.model.progress;

import com.remcoil.skiron.database.entity.ControlType;
import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.database.entity.Specification;

import java.util.Map;

public record KitBriefProgress(
        long specificationId,
        String specificationTitle,
        int testedPercentage,
        long kitId,
        String kitNumber,
        int kitSize,
        int acceptancePercentage,
        int batchesQuantity,
        Map<KitStat, Integer> stats,
        Map<ControlType, Integer> controlProgress
) {
    public KitBriefProgress(
            Specification specification,
            Kit kit,
            Map<KitStat, Integer> stats,
            Map<ControlType, Integer> controlProgress
    ) {
        this(
                specification.getId(),
                specification.getSpecificationTitle(),
                specification.getTestedPercentage(),
                kit.getId(),
                kit.getKitNumber(),
                kit.getBatchSize() * kit.getBatchesQuantity(),
                kit.getAcceptancePercentage(),
                kit.getBatchesQuantity(),
                stats,
                controlProgress
        );
    }
}
