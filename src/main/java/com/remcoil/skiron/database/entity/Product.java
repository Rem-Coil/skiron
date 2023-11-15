package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer productNumber;
    Boolean active = true;
    Boolean locked = false;
    Long batchId;

    public Product(Integer productNumber, Long batchId) {
        this.productNumber = productNumber;
        this.batchId = batchId;
    }
}
