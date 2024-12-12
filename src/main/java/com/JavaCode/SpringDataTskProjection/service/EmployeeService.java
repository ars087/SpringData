package com.JavaCode.SpringDataTskProjection.service;

import com.JavaCode.SpringDataTskProjection.model.Employee;
import com.JavaCode.SpringDataTskProjection.projection.EmployeeProjection;
import com.JavaCode.SpringDataTskProjection.repository.EmployeeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
            return true;
        } catch (DataAccessException e) {

        } catch (Exception e) {

        }
        return false;
    }

    public List<EmployeeProjection> getAllEmployees() {
        return employeeRepository.findAllProjectedBy();
    }

    @Transactional
    public Optional<Employee> updateEmployeeSalary(Long id, double newSalary) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setSalary(newSalary);
            return employeeRepository.save(employee);
        });
    }

    @Transactional
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
