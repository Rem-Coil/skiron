package com.remcoil.skiron.model.action.acceptance;

import com.remcoil.skiron.database.entity.AcceptanceAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AcceptanceActionBrief(
        long id,
        LocalDateTime doneTime,
        long productId,
        UUID employeeId
) {
    public static AcceptanceActionBrief fromEntity(AcceptanceAction action) {
        return new AcceptanceActionBrief(
                action.getId(),
                action.getDoneTime(),
                action.getProductId(),
                action.getEmployeeId()
        );
    }

    public static List<AcceptanceActionBrief> fromEntities(List<AcceptanceAction> acceptanceActions) {
        return acceptanceActions.stream()
                .map(AcceptanceActionBrief::fromEntity)
                .toList();
    }

    public AcceptanceAction toEntity() {
        return new AcceptanceAction(
                id,
                doneTime,
                productId,
                employeeId
        );
    }
}
