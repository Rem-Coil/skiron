package com.remcoil.skiron.model.action.acceptance;

import com.remcoil.skiron.database.entity.AcceptanceAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AcceptanceActionPostRequest(
        LocalDateTime doneTime,
        List<Long> productsId,
        UUID employeeId
) {
    public List<AcceptanceAction> toEntity() {
        return productsId.stream()
                .map(productId -> new AcceptanceAction(doneTime, productId, employeeId))
                .toList();
    }
}
