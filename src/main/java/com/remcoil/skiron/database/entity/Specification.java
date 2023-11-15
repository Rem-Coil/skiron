package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "specifications")
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specificationTitle;
    private String productType;
    private Integer testedPercentage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "specification")
    private List<OperationType> operationTypes = new ArrayList<>();

    public Specification(Long id, String specificationTitle, String productType, Integer testedPercentage) {
        this.id = id;
        this.specificationTitle = specificationTitle;
        this.productType = productType;
        this.testedPercentage = testedPercentage;
    }

    public Specification(String specificationTitle, String productType, Integer testedPercentage) {
        this.specificationTitle = specificationTitle;
        this.productType = productType;
        this.testedPercentage = testedPercentage;
    }
}
