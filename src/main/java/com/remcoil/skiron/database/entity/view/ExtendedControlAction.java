package com.remcoil.skiron.database.entity.view;

import com.remcoil.skiron.database.entity.ControlType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Immutable
@Table(name = "extended_control_actions")
public class ExtendedControlAction {
    @Id
    Long id;

    LocalDateTime doneTime;
    Boolean successful;

    @Enumerated(EnumType.STRING)
    ControlType controlType;

    String comment;
    Boolean needRepair;
    Long operationTypeId;
    UUID employeeId;
    Long productId;
    Boolean active;
    Long batchId;
    Long kitId;
}
