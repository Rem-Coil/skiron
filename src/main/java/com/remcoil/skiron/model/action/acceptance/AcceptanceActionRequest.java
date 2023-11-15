package com.remcoil.skiron.model.action.acceptance;

import com.remcoil.skiron.database.entity.AcceptanceAction;

import java.time.LocalDateTime;
import java.util.UUID;

public record AcceptanceActionRequest(
        LocalDateTime doneTime,
        long productId,
        UUID employeeId
) {
    public AcceptanceAction toEntity() {
        return new AcceptanceAction(
                doneTime,
                productId,
                employeeId
        );
    }
}
