package com.remcoil.skiron.model.action.control;

import com.remcoil.skiron.database.entity.ControlAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ControlActionBrief(
        long id,
        LocalDateTime doneTime,
        boolean successful,
        ControlAction.ControlType controlType,
        String comment,
        boolean needRepair,
        long operationTypeId,
        long productId,
        UUID employeeId
) {
    public static ControlActionBrief fromEntity(ControlAction controlAction) {
        return new ControlActionBrief(
                controlAction.getId(),
                controlAction.getDoneTime(),
                controlAction.getSuccessful(),
                controlAction.getControlType(),
                controlAction.getComment(),
                controlAction.getNeedRepair(),
                controlAction.getOperationTypeId(),
                controlAction.getProductId(),
                controlAction.getEmployeeId()
        );
    }

    public static List<ControlActionBrief> fromEntities(List<ControlAction> controlActions) {
        return controlActions.stream()
                .map(ControlActionBrief::fromEntity)
                .toList();
    }

    public ControlAction toEntity() {
        return new ControlAction(
                id,
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
