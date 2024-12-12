package com.JavaCode.SpringDataTskProjection.repository;

import com.JavaCode.SpringDataTskProjection.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
