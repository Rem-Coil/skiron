package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.action.acceptance.AcceptanceActionBrief;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionPostRequest;
import com.remcoil.skiron.service.AcceptanceActionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/acceptance-action")
public class AcceptanceActionController {
    private final AcceptanceActionService acceptanceActionService;

    public AcceptanceActionController(AcceptanceActionService acceptanceActionService) {
        this.acceptanceActionService = acceptanceActionService;
    }

    @GetMapping
    public List<AcceptanceActionBrief> getAll() {
        return acceptanceActionService.getAll();
    }

    @PostMapping
    public AcceptanceActionBrief create(@RequestBody AcceptanceActionPostRequest actionRequest) {
        return acceptanceActionService.create(actionRequest);
    }
}
