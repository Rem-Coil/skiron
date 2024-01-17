package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.progress.KitBriefProgress;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitPostRequest;
import com.remcoil.skiron.model.progress.KitProgress;
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

    @GetMapping("/progress")
    public List<KitBriefProgress> getProgress() {
        return kitService.getProgress();
    }

    @GetMapping("/{id}/progress")
    public KitProgress getProgressById(@PathVariable("id") long id) {
        return kitService.getProgressById(id);
    }

    @PostMapping
    public KitFull create(@RequestBody KitPostRequest kitRequest) {
        return kitService.create(kitRequest);
    }

    @PutMapping
    public void update(@RequestBody KitFull kit) {
        kitService.update(kit);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        kitService.deleteById(id);
    }

}
