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
@Table(name = "control_actions")
public class ControlAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime doneTime;
    Boolean successful;

    @Enumerated(EnumType.STRING)
    ControlType controlType;

    String comment;
    Boolean needRepair;
    Long operationTypeId;
    Long productId;
    UUID employeeId;

    public enum ControlType {
        OTK,
        TESTING
    }

    public ControlAction(LocalDateTime doneTime, Boolean successful, ControlType controlType, String comment, Boolean needRepair, Long operationTypeId, Long productId, UUID employeeId) {
        this.doneTime = doneTime;
        this.successful = successful;
        this.controlType = controlType;
        this.comment = comment;
        this.needRepair = needRepair;
        this.operationTypeId = operationTypeId;
        this.productId = productId;
        this.employeeId = employeeId;
    }
}
