package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.ControlActionRepository;
import com.remcoil.skiron.model.action.control.ControlActionBrief;
import com.remcoil.skiron.model.action.control.ControlActionPostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlActionService {
    private final ControlActionRepository controlActionRepository;

    public ControlActionService(ControlActionRepository controlActionRepository) {
        this.controlActionRepository = controlActionRepository;
    }

    public List<ControlActionBrief> getAll() {
        return controlActionRepository.findAll().stream()
                .map(ControlActionBrief::fromEntity)
                .toList();
    }

    public ControlActionBrief create(ControlActionPostRequest actionRequest) {
        return ControlActionBrief.fromEntity(
                controlActionRepository.save(actionRequest.toEntity())
        );
    }
}
