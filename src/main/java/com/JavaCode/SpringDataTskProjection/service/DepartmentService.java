package com.JavaCode.SpringDataTskProjection.service;

import com.JavaCode.SpringDataTskProjection.model.Department;
import com.JavaCode.SpringDataTskProjection.repository.DepartmentRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public boolean saveDepartment(Department department) {

        try {
            departmentRepository.save(department);
            return true;
        } catch (DataAccessException exception) {
            return false;
        }

    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    @Transactional
    public Optional<Department> updateDepartment(Long id, Department updatedDepartment) {
        return departmentRepository.findById(id).map(existingDepartment -> {
            existingDepartment.setName(updatedDepartment.getName());
            return departmentRepository.save(existingDepartment);
        });
    }

    @Transactional
    public boolean deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
