package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.specification.SpecificationBrief;
import com.remcoil.skiron.model.specification.SpecificationFull;
import com.remcoil.skiron.model.specification.SpecificationPostRequest;
import com.remcoil.skiron.service.SpecificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/specification")
public class SpecificationController {
    private final SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @GetMapping
    public List<SpecificationBrief> getAll() {
        return specificationService.getAll();
    }

    @GetMapping("/{id}")
    public SpecificationFull getById(@PathVariable Long id) {
        return specificationService.getById(id);
    }

    @PostMapping
    public SpecificationFull create(@RequestBody SpecificationPostRequest specification) {
        return specificationService.create(specification);
    }

    @PutMapping
    public void update(@RequestBody SpecificationFull specificationFull) {
        specificationService.update(specificationFull);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        specificationService.deleteById(id);
    }
}
