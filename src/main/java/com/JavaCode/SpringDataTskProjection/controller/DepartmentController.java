package com.JavaCode.SpringDataTskProjection.controller;


import com.JavaCode.SpringDataTskProjection.model.Department;
import com.JavaCode.SpringDataTskProjection.service.DepartmentService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")

public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<?> saveDepartment(@RequestBody Department department) {

          if (departmentService.saveDepartment(department)){
            return   ResponseEntity.ok().body("Сохранено");
          }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сохранения");

    }

    @GetMapping
    public List<?> getAllDepartments() {
        return departmentService.getAllDepartments();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department newNameDepartment) {
        Optional<Department> optionalDepartment = departmentService.updateDepartment(id, newNameDepartment);

        if (optionalDepartment.isPresent()) {
            return ResponseEntity.ok("Данные обновлены");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (departmentService.deleteDepartment(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}