package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.control.ControlActionBrief;
import com.remcoil.skiron.model.action.control.ControlActionRequest;
import com.remcoil.skiron.service.ControlActionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControlActionController {
    private final ControlActionService controlActionService;

    public ControlActionController(ControlActionService controlActionService) {
        this.controlActionService = controlActionService;
    }

    @GetMapping("/control_action")
    public List<ControlActionBrief> getAll() {
        return controlActionService.getAll();
    }

    @PostMapping("/control_action")
    public ControlActionBrief create(@RequestBody ControlActionRequest actionRequest) {
        return controlActionService.create(actionRequest);
    }
}
