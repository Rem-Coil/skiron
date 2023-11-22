package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.ActionRepository;
import com.remcoil.skiron.model.action.ActionBrief;
import com.remcoil.skiron.model.action.ActionPostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<ActionBrief> getAll() {
        return actionRepository.findAll().stream()
                .map(ActionBrief::fromEntity)
                .toList();
    }

    public List<ActionBrief> create(ActionPostRequest actionRequest) {
        return ActionBrief.fromEntities(
                actionRepository.saveAll(actionRequest.toEntities())
        );
    }
}
