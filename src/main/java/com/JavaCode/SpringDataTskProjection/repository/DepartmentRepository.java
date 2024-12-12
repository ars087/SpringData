package com.JavaCode.SpringDataTskProjection.repository;

import com.JavaCode.SpringDataTskProjection.madel.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
