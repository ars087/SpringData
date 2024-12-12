package com.JavaCode.SpringDataTskProjection.controller;

import com.JavaCode.SpringDataTskProjection.madel.Employee;
import com.JavaCode.SpringDataTskProjection.projection.EmployeeProjection;
import com.JavaCode.SpringDataTskProjection.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {

        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<EmployeeProjection> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<?> updateEmployeeSalary(@PathVariable Long id, @RequestParam Double salary) {
        Optional<Employee> optionalEmployee = employeeService.updateEmployeeSalary(id, salary);

        if (optionalEmployee.isPresent()) {

            return ResponseEntity.ok("Данные для сотрудника обновлены");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сотрудник не найден");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok().body("Сотрудник удален");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сотрудник не найден");
        }

    }
}