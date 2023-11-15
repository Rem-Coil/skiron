package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.ControlActionRepository;
import com.remcoil.skiron.model.action.control.ControlActionBrief;
import com.remcoil.skiron.model.action.control.ControlActionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ControlActionService {
    private final ControlActionRepository controlActionRepository;

    public ControlActionService(ControlActionRepository controlActionRepository) {
        this.controlActionRepository = controlActionRepository;
    }

    public List<ControlActionBrief> getAll() {
        return controlActionRepository.findAll().stream()
                .map(ControlActionBrief::fromEntity)
                .collect(Collectors.toList());
    }

    public ControlActionBrief create(ControlActionRequest actionRequest) {
        return ControlActionBrief.fromEntity(
                controlActionRepository.save(actionRequest.toEntity())
        );
    }
}
