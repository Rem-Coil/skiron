package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.database.entity.Role;
import com.remcoil.skiron.database.repository.EmployeeRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.exception.ForbiddenRoleException;
import com.remcoil.skiron.model.employee.EmployeeDetails;
import com.remcoil.skiron.model.employee.EmployeePublicData;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeePublicData> getAll(boolean activeOnly) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getActive() || !activeOnly)
                .map(EmployeePublicData::fromEntity)
                .toList();
    }

    public EmployeePublicData getById(UUID id) {
        return EmployeePublicData.fromEntity(
                employeeRepository.findById(id).orElseThrow(() -> new EntryDoesNotExistException("Not Found"))
        );
    }

    @Transactional
    public void updateActiveStatus(UUID id, boolean status) {
        employeeRepository.updateActiveStatus(id, status);
    }

    @Transactional
    public void updateRole(UUID id, Role role) {
        if (role == Role.ADMIN) {
            throw new ForbiddenRoleException("Admin creation");
        } else {
            employeeRepository.updateRole(id, role);
        }
    }

    protected void update(Employee employee) {
        employeeRepository.save(employee);
    }

    protected Optional<Employee> getByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    protected Employee create(Employee employee) {
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

    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }
}
