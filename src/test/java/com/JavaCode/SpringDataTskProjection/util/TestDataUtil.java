package com.JavaCode.SpringDataTskProjection.util;

import com.JavaCode.SpringDataTskProjection.model.Employee;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Иван");
        employee.setLastName("Иванов");
        employee.setPosition("Разработчик");
        employee.setSalary(50000.01);
        employee.setDepartment(null);
        return employee;
    }
}
