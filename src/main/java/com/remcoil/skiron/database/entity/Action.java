package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime doneTime;
    Boolean repair;
    Long operationTypeId;
    Long productId;
    UUID employeeId;

    public Action(LocalDateTime doneTime, Boolean repair, Long operationTypeId, Long productId, UUID employeeId) {
        this.doneTime = doneTime;
        this.repair = repair;
        this.operationTypeId = operationTypeId;
        this.productId = productId;
        this.employeeId = employeeId;
    }
}
