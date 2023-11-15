package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.acceptance.AcceptanceActionBrief;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionRequest;
import com.remcoil.skiron.service.AcceptanceActionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AcceptanceActionController {
    private final AcceptanceActionService acceptanceActionService;

    public AcceptanceActionController(AcceptanceActionService acceptanceActionService) {
        this.acceptanceActionService = acceptanceActionService;
    }

    @GetMapping("/acceptance_action")
    public List<AcceptanceActionBrief> getAll() {
        return acceptanceActionService.getAll();
    }

    @PostMapping("/acceptance_action")
    public AcceptanceActionBrief create(@RequestBody AcceptanceActionRequest actionRequest) {
        return acceptanceActionService.create(actionRequest);
    }
}
