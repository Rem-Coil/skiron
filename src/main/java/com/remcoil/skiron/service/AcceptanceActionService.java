package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.AcceptanceActionRepository;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionBrief;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptanceActionService {
    private final AcceptanceActionRepository acceptanceActionRepository;

    public AcceptanceActionService(AcceptanceActionRepository acceptanceActionRepository) {
        this.acceptanceActionRepository = acceptanceActionRepository;
    }

    public List<AcceptanceActionBrief> getAll() {
        return acceptanceActionRepository.findAll().stream()
                .map(AcceptanceActionBrief::fromEntity)
                .collect(Collectors.toList());
    }

    public AcceptanceActionBrief create(AcceptanceActionRequest actionRequest) {
        return AcceptanceActionBrief.fromEntity(
                acceptanceActionRepository.save(actionRequest.toEntity())
        );
    }
}
