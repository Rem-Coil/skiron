package com.remcoil.skiron.model.specification;

import com.remcoil.skiron.database.entity.Specification;

public record SpecificationBrief(
        long id,
        String specificationTitle,
        String productType,
        int testedPercentage
) {
    public static SpecificationBrief fromEntity(Specification specification) {
        return new SpecificationBrief(
                specification.getId(),
                specification.getSpecificationTitle(),
                specification.getProductType(),
                specification.getTestedPercentage());
    }
}
