package com.remcoil.skiron.model.action;

import com.remcoil.skiron.database.entity.Action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActionPostRequest(
        LocalDateTime doneTime,
        boolean repair,
        long operationTypeId,
        List<Long> productsId,
        UUID employeeId
) {
    public List<Action> toEntities() {
        return productsId.stream()
                .map(id -> new Action(doneTime, repair, operationTypeId, id, employeeId))
                .toList();
    }
}
