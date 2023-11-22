package com.remcoil.skiron.model.specification;

import com.remcoil.skiron.database.entity.Specification;
import com.remcoil.skiron.model.operation.OperationTypeBrief;

import java.util.List;

public record SpecificationPostRequest(
        String specificationTitle,
        String productType,
        int testedPercentage,
        List<OperationTypeBrief> operationTypes
) {

    public Specification toEntity() {
        return new Specification(
                specificationTitle,
                productType,
                testedPercentage
        );
    }
}
