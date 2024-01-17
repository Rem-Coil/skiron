package com.remcoil.skiron.model.action.control;

import com.remcoil.skiron.database.entity.ControlAction;
import com.remcoil.skiron.database.entity.ControlType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ControlActionPostRequest(
        LocalDateTime doneTime,
        boolean successful,
        ControlType controlType,
        String comment,
        boolean needRepair,
        long operationTypeId,
        List<Long> productsId,
        UUID employeeId
) {
    public List<ControlAction> toEntities() {
        return productsId.stream()
                .map(productId -> new ControlAction(doneTime, successful, controlType, comment, needRepair, operationTypeId, productId, employeeId))
                .toList();
    }
}
