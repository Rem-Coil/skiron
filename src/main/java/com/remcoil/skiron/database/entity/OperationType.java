package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "operation_types")
public class OperationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Integer sequenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id")
    private Specification specification;

    public OperationType(Long id, String type, Integer sequenceNumber, Specification specification) {
        this.id = id;
        this.type = type;
        this.sequenceNumber = sequenceNumber;
        this.specification = specification;
    }

    public OperationType(String type, Integer sequenceNumber, Specification specification) {
        this.type = type;
        this.sequenceNumber = sequenceNumber;
        this.specification = specification;
    }
}
