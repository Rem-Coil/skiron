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
@Table(name = "extended_actions")
public class ExtendedAction {
    @Id
    Long id;

    LocalDateTime doneTime;
    Boolean repair;
    Long operationTypeId;
    UUID employeeId;
    Long productId;
    Boolean active;
    Long batchId;
    Long kitId;
}
