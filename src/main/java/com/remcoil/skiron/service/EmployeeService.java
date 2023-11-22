package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.database.repository.EmployeeRepository;
import com.remcoil.skiron.model.employee.EmployeeDetails;
import com.remcoil.skiron.model.employee.EmployeePublicData;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeePublicData> getAll() {
        return employeeRepository.findAll().stream()
                .map(EmployeePublicData::fromEntity)
                .toList();
    }

    public Optional<Employee> getByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = getByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found"));

        return new EmployeeDetails(
                employee.getId(),
                employee.getPhone(),
                employee.getPassword(),
                employee.getRole()
        );
    }
}
