package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitPostRequest;
import com.remcoil.skiron.service.KitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/kit")
public class KitController {
    private final KitService kitService;

    public KitController(KitService kitService) {
        this.kitService = kitService;
    }

    @GetMapping
    public List<KitFull> getAll() {
        return kitService.getAll();
    }

    @PostMapping
    public KitFull create(@RequestBody KitPostRequest kitRequest) {
        return kitService.create(kitRequest);
    }

    @PutMapping
    public void update(@RequestBody KitFull kit) {
        kitService.update(kit);
    }
}
