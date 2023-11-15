package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer batchNumber;
    Long kitId;

    public Batch(Integer batchNumber, Long kitId) {
        this.batchNumber = batchNumber;
        this.kitId = kitId;
    }
}
