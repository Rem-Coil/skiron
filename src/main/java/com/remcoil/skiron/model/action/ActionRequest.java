package com.remcoil.skiron.model.action;

import com.remcoil.skiron.database.entity.Action;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActionRequest(
        LocalDateTime doneTime,
        boolean repair,
        long operationTypeId,
        long productId,
        UUID employeeId
) {
    public Action toEntity() {
        return new Action(
                doneTime,
                repair,
                operationTypeId,
                productId,
                employeeId
        );
    }
}
