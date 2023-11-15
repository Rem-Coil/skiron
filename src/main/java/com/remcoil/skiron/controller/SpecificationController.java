package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.specification.SpecificationBrief;
import com.remcoil.skiron.model.specification.SpecificationFull;
import com.remcoil.skiron.model.specification.SpecificationRequest;
import com.remcoil.skiron.service.SpecificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpecificationController {
    private final SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @GetMapping("/specification")
    public List<SpecificationBrief> getAll() {
        return specificationService.getAll();
    }

    @GetMapping("/specification/{id}")
    public SpecificationFull getById(@PathVariable Long id) {
        return specificationService.getById(id);
    }

    @PostMapping("/specification")
    public SpecificationFull create(@RequestBody SpecificationRequest specification) {
        return specificationService.create(specification);
    }

    @PutMapping("/specification")
    public void update(@RequestBody SpecificationFull specificationFull) {
        specificationService.update(specificationFull);
    }

    @DeleteMapping("/specification/{id}")
    public void deleteById(@PathVariable Long id) {
        specificationService.deleteById(id);
    }
}
