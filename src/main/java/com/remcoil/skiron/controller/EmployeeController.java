package com.remcoil.skiron.controller;

import com.remcoil.skiron.database.entity.Role;
import com.remcoil.skiron.model.employee.EmployeeFull;
import com.remcoil.skiron.model.employee.EmployeePublicData;
import com.remcoil.skiron.service.AuthenticationService;
import com.remcoil.skiron.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@RequestMapping("api/v4/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    @GetMapping()
    public List<EmployeePublicData> getAll(@RequestParam(required = false, name = "active-only", defaultValue = "true") boolean activeOnly) {
        return employeeService.getAll(activeOnly);
    }

    @GetMapping("/{id}")
    public EmployeePublicData getById(@PathVariable("id") UUID id) {
        return employeeService.getById(id);
    }

    @PatchMapping("/{id}/status")
    public void updateActiveStatus(@PathVariable("id") UUID id, @RequestParam(name = "active") boolean status) {
        employeeService.updateActiveStatus(id, status);
    }

    @PatchMapping("/{id}/role")
    public void updateRole(@PathVariable("id") UUID id, @RequestParam(name = "role") Role role) {
        employeeService.updateRole(id, role);
    }

    @PutMapping
    public void update(@RequestBody EmployeeFull employeeFull) {
        authenticationService.updateEmployee(employeeFull);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
    }
}
