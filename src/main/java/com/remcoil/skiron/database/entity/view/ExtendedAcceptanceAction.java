package com.remcoil.skiron.database.entity.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Immutable
@Table(name = "extended_acceptance_actions")
public class ExtendedAcceptanceAction {
    @Id
    Long id;

    LocalDateTime doneTime;
    UUID employeeId;
    Long productId;
    Boolean active;
    Long batchId;
    Long kitId;
}
