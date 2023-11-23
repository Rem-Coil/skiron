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
@Table(name = "acceptance_actions")
public class AcceptanceAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime doneTime;
    Long productId;
    UUID employeeId;

    public AcceptanceAction(LocalDateTime doneTime, Long productId, UUID employeeId) {
        this.doneTime = doneTime;
        this.productId = productId;
        this.employeeId = employeeId;
    }
}
