package com.remcoil.skiron.model.operation;

import com.remcoil.skiron.database.entity.OperationType;
import com.remcoil.skiron.database.entity.Specification;

public record OperationTypeFull(
        long id,
        String type,
        int sequenceNumber,
        long specificationId
) {
    public static OperationTypeFull fromEntity(OperationType operationType) {
        return new OperationTypeFull(
                operationType.getId(),
                operationType.getType(),
                operationType.getSequenceNumber(),
                operationType.getSpecification().getId()
        );
    }

    public OperationType toEntity(Specification specification) {
        return new OperationType(
                id,
                type,
                sequenceNumber,
                specification
        );
    }
}
