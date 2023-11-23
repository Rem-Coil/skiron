package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.control.ControlActionBrief;
import com.remcoil.skiron.model.action.control.ControlActionPostRequest;
import com.remcoil.skiron.service.ControlActionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/control-action")
public class ControlActionController {
    private final ControlActionService controlActionService;

    public ControlActionController(ControlActionService controlActionService) {
        this.controlActionService = controlActionService;
    }

    @GetMapping
    public List<ControlActionBrief> getAll() {
        return controlActionService.getAll();
    }

    @PostMapping
    public List<ControlActionBrief> create(@RequestBody ControlActionPostRequest actionRequest) {
        return controlActionService.create(actionRequest);
    }

    @PutMapping
    public ControlActionBrief update(@RequestBody ControlActionBrief actionBrief) {
        return controlActionService.update(actionBrief);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        controlActionService.deleteById(id);
    }
}
