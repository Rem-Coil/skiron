package com.remcoil.skiron.service;

import com.remcoil.skiron.exception.EntryAlreadyExistException;
import com.remcoil.skiron.exception.InvalidPasswordException;
import com.remcoil.skiron.model.employee.EmployeeCredentials;
import com.remcoil.skiron.model.employee.EmployeeDetails;
import com.remcoil.skiron.model.employee.EmployeeRegisterData;
import com.remcoil.skiron.model.response.JwtResponse;
import com.remcoil.skiron.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager, EmployeeService employeeService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authenticateEmployee(EmployeeCredentials employeeCredentials) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        employeeCredentials.phone(),
                        employeeCredentials.password())
        );

        return new JwtResponse(
                jwtUtils.generateToken((EmployeeDetails) authenticate.getPrincipal())
        );
    }

    @Transactional
    public JwtResponse registerEmployee(EmployeeRegisterData employeeRegisterData) {
        if (!employeeRegisterData.passwordIsValid()) {
            throw new InvalidPasswordException("Not equal");
        }
        if (employeeService.getByPhone(employeeRegisterData.phone()).isPresent()) {
            throw new EntryAlreadyExistException("Already exist");
        }
        EmployeeDetails employeeDetails = EmployeeDetails.fromEntity(
                employeeService.create(employeeRegisterData.toEntity(passwordEncoder))
        );
        return new JwtResponse(
                jwtUtils.generateToken(employeeDetails)
        );
    }
}
