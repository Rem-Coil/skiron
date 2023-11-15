package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitRequest;
import com.remcoil.skiron.service.KitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KitController {
    private final KitService kitService;

    public KitController(KitService kitService) {
        this.kitService = kitService;
    }

    @GetMapping("/kit")
    public List<KitFull> getAll() {
        return kitService.getAll();
    }

    @PostMapping("/kit")
    public KitFull create(@RequestBody KitRequest kitRequest) {
        return kitService.create(kitRequest);
    }
}
