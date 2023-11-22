package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

}
