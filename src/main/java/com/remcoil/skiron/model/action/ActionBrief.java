package com.remcoil.skiron.model.action;

import com.remcoil.skiron.database.entity.Action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActionBrief(
        long id,
        LocalDateTime doneTime,
        boolean repair,
        long operationTypeId,
        long productId,
        UUID employeeId
) {
    public static ActionBrief fromEntity(Action action) {
        return new ActionBrief(
                action.getId(),
                action.getDoneTime(),
                action.getRepair(),
                action.getOperationTypeId(),
                action.getProductId(),
                action.getEmployeeId()
        );
    }

    public static List<ActionBrief> fromEntities(List<Action> actions) {
        return actions.stream()
                .map(ActionBrief::fromEntity)
                .toList();
    }

    public Action toEntity() {
        return new Action(
                id,
                doneTime,
                repair,
                operationTypeId,
                productId,
                employeeId
        );
    }
}
