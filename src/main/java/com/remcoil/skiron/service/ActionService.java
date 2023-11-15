package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.ActionRepository;
import com.remcoil.skiron.model.action.ActionBrief;
import com.remcoil.skiron.model.action.ActionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<ActionBrief> getAll() {
        return actionRepository.findAll().stream()
                .map(ActionBrief::fromEntity)
                .collect(Collectors.toList());
    }

    public ActionBrief create(ActionRequest actionRequest) {
        return ActionBrief.fromEntity(
                actionRepository.save(actionRequest.toEntity())
        );
    }
}
