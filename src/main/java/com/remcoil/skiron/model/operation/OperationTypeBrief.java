package com.remcoil.skiron.model.operation;

import com.remcoil.skiron.database.entity.OperationType;
import com.remcoil.skiron.database.entity.Specification;

public record OperationTypeBrief(
        String type,
        int sequenceNumber
) {
    public OperationType toEntity(Specification specification) {
        return new OperationType(
                type,
                sequenceNumber,
                specification
        );
    }
}
