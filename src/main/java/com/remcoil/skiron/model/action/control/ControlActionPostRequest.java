package com.remcoil.skiron.model.action.control;

import com.remcoil.skiron.database.entity.ControlAction;

import java.time.LocalDateTime;
import java.util.UUID;

public record ControlActionPostRequest(
        LocalDateTime doneTime,
        boolean successful,
        ControlAction.ControlType controlType,
        String comment,
        boolean needRepair,
        long operationTypeId,
        long productId,
        UUID employeeId
) {
    public ControlAction toEntity() {
        return new ControlAction(
                doneTime,
                successful,
                controlType,
                comment,
                needRepair,
                operationTypeId,
                productId,
                employeeId
        );
    }
}
