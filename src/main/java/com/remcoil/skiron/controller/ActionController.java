package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.ActionBrief;
import com.remcoil.skiron.model.action.ActionRequest;
import com.remcoil.skiron.service.ActionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActionController {
    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/action")
    public List<ActionBrief> getAll() {
        return actionService.getAll();
    }

    @PostMapping("/action")
    public ActionBrief create(@RequestBody ActionRequest actionRequest) {
        return actionService.create(actionRequest);
    }
}
