package com.JavaCode.SpringDataTskProjection.repository;

import com.JavaCode.SpringDataTskProjection.madel.Employee;
import com.JavaCode.SpringDataTskProjection.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value =
        "SELECT CONCAT(e.first_name, ' ', e.last_name) AS fullName, e.position AS position, d.name AS departmentName " +
            "FROM employee e JOIN department d ON e.department_id = d.id", nativeQuery = true)
    List<EmployeeProjection> findAllProjectedBy();
}
