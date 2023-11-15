package com.remcoil.skiron.model.specification;

import com.remcoil.skiron.database.entity.Specification;
import com.remcoil.skiron.model.operation.OperationTypeFull;

import java.util.List;
import java.util.stream.Collectors;

public record SpecificationFull(
        long id,
        String specificationTitle,
        String productType,
        int testedPercentage,
        List<OperationTypeFull> operationTypes
) {
    public static SpecificationFull fromEntity(Specification specification) {
        return new SpecificationFull(
                specification.getId(),
                specification.getSpecificationTitle(),
                specification.getProductType(),
                specification.getTestedPercentage(),
                specification.getOperationTypes()
                        .stream()
                        .map(OperationTypeFull::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public Specification toEntityWithOperations() {
        Specification specification = new Specification(
                id,
                specificationTitle,
                productType,
                testedPercentage
        );

        specification.setOperationTypes(operationTypes
                .stream()
                .map(it -> it.toEntity(specification))
                .collect(Collectors.toList())
        );

        return specification;
    }

    public Specification toEntity() {
        return new Specification(
                id,
                specificationTitle,
                productType,
                testedPercentage
        );
    }
}
