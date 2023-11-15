package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "kits")
public class Kit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String kitNumber;
    Integer batchesQuantity;
    Integer batchSize;
    Integer acceptancePercentage;
    Long specificationId;

    public Kit(String kitNumber, Integer batchesQuantity, Integer batchSize, Integer acceptancePercentage, Long specificationId) {
        this.kitNumber = kitNumber;
        this.batchesQuantity = batchesQuantity;
        this.batchSize = batchSize;
        this.acceptancePercentage = acceptancePercentage;
        this.specificationId = specificationId;
    }

    public int productQuantity() {
        return batchesQuantity * batchSize;
    }
}
