package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.AcceptanceActionRepository;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionBrief;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionPostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptanceActionService {
    private final AcceptanceActionRepository acceptanceActionRepository;

    public AcceptanceActionService(AcceptanceActionRepository acceptanceActionRepository) {
        this.acceptanceActionRepository = acceptanceActionRepository;
    }

    public List<AcceptanceActionBrief> getAll() {
        return acceptanceActionRepository.findAll().stream()
                .map(AcceptanceActionBrief::fromEntity)
                .toList();
    }

    public AcceptanceActionBrief create(AcceptanceActionPostRequest actionRequest) {
        return AcceptanceActionBrief.fromEntity(
                acceptanceActionRepository.save(actionRequest.toEntity())
        );
    }
}
