package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.ActionBrief;
import com.remcoil.skiron.model.action.ActionPostRequest;
import com.remcoil.skiron.service.ActionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/action")
public class ActionController {
    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public List<ActionBrief> getAll() {
        return actionService.getAll();
    }

    @PostMapping
    public List<ActionBrief> create(@RequestBody ActionPostRequest actionRequest) {
        return actionService.create(actionRequest);
    }

    @PutMapping
    public ActionBrief update(@RequestBody ActionBrief actionBrief) {
        return actionService.update(actionBrief);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        actionService.deleteById(id);
    }
}
