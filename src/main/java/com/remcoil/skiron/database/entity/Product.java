package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    public Product deactivated() {
        return new Product(id, productNumber, false, locked, batchId);
    }

    public Product getNew() {
        return new Product(productNumber, batchId);
    }
}
