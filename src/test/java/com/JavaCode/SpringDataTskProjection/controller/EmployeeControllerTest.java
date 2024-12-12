package com.JavaCode.SpringDataTskProjection.controller;

import com.JavaCode.SpringDataTskProjection.model.Employee;
import com.JavaCode.SpringDataTskProjection.projection.EmployeeProjection;
import com.JavaCode.SpringDataTskProjection.service.EmployeeService;
import com.JavaCode.SpringDataTskProjection.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)

@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllEmploy() throws Exception {

        List<EmployeeProjection> projections = Arrays.asList(
            new EmployeeProjection() {
                @Override
                public String getFullName() {
                    return "John Doe";
                }

                @Override
                public String getPosition() {
                    return "Developer";
                }

                @Override
                public String getDepartmentName() {
                    return "IT";
                }
            }
        );


        when(employeeService.getAllEmployees()).thenReturn(projections);

        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].fullName").value("John Doe"))
            .andExpect(jsonPath("$[0].position").value("Developer"))
            .andExpect(jsonPath("$[0].departmentName").value("IT"));
    }

    @Test
    public void testUpdateEmployeeSalary_Success() throws Exception {

        Long employeeId = 1L;
        Double newSalary = 75000.0;

        Employee employee = new Employee("Иван", "Иванов", "Разработчик", 60000.0, null);
        employee.setId(employeeId);

        when(employeeService.updateEmployeeSalary(employeeId, newSalary))
            .thenReturn(Optional.of(employee));

        mockMvc.perform(put("/api/employees/{id}/salary", employeeId)
                .param("salary", String.valueOf(newSalary)))
            .andExpect(status().isOk())
            .andExpect(content().string("Данные для сотрудника обновлены")); // Ожидаем сообщение в теле ответа
    }

    @Test
    public void testUpdateEmployeeSalary_NotFound() throws Exception {

        Long employeeId = 1L;
        Double newSalary = 75000.0;

        when(employeeService.updateEmployeeSalary(employeeId, newSalary))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/employees/{id}/salary", employeeId)
                .param("salary", String.valueOf(newSalary)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Сотрудник не найден"));
    }

    @Test
    public void testDeleteEmployee_Success() throws Exception {

        Long employeeId = 1L;

        when(employeeService.deleteEmployee(employeeId)).thenReturn(true);
        mockMvc.perform(delete("/api/employees/{id}", employeeId))
            .andExpect(status().isOk())
            .andExpect(content().string("Сотрудник удален"));
    }

    @Test
    public void testDeleteEmployee_NotFound() throws Exception {

        Long employeeId = 1L;

        when(employeeService.deleteEmployee(employeeId)).thenReturn(false);
        mockMvc.perform(delete("/api/employees/{id}", employeeId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Сотрудник не найден"));
    }

    @Test
    public void testSaveEmployee_Success() throws Exception {

        Employee employee = TestDataUtil.createTestEmployee();

        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(true);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(content().string("Сохранено"));
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));

    }
}