package com.remcoil.skiron.controller;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public List<Employee> getAll() {
        return employeeService.getAll();
    }
}
