package com.remcoil.skiron.controller;

import com.remcoil.skiron.model.employee.EmployeeCredentials;
import com.remcoil.skiron.model.employee.EmployeeRegisterData;
import com.remcoil.skiron.model.response.JwtResponse;
import com.remcoil.skiron.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v4/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody EmployeeCredentials employeeCredentials) {
        return authenticationService.authenticateEmployee(employeeCredentials);
    }

    @PostMapping("/sign-up")
    public JwtResponse signUp(@RequestBody EmployeeRegisterData employeeRegisterData) {
        return authenticationService.registerEmployee(employeeRegisterData);
    }
}
