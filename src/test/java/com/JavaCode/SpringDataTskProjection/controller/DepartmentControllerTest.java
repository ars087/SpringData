package com.JavaCode.SpringDataTskProjection.controller;

import com.JavaCode.SpringDataTskProjection.model.Department;
import com.JavaCode.SpringDataTskProjection.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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


@WebMvcTest(DepartmentController.class)

@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSaveDepartment_Success() throws Exception {

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setEmployees(new ArrayList<>());

        when(departmentService.saveDepartment(any(Department.class))).thenReturn(true);

        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)))
            .andExpect(status().isOk())
            .andExpect(content().string("Сохранено"));

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void testSaveDepartment_Failure() throws Exception {

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        when(departmentService.saveDepartment(Mockito.any(Department.class))).thenReturn(false);

        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Ошибка сохранения"));

        verify(departmentService, times(1)).saveDepartment(Mockito.any(Department.class));
    }

    @Test
    void testGetAllDepartments() throws Exception {
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("IT");
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("HR");

        List<Department> departments = Arrays.asList(department1, department2);

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/api/departments")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("IT"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("HR"));

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void testUpdateDepartment_Success() throws Exception {
        Department updatedDepartment = new Department(1L, "New Department Name", null);

        when(departmentService.updateDepartment(eq(1L), any(Department.class)))
            .thenReturn(Optional.of(updatedDepartment));

        mockMvc.perform(put("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDepartment)))
            .andExpect(status().isOk())
            .andExpect(content().string("Данные обновлены"));

        verify(departmentService, times(1)).updateDepartment(eq(1L), any(Department.class));
    }

    @Test
    void testUpdateDepartment_NotFound() throws Exception {
        Department updatedDepartment = new Department(1L, "New Department Name", null);

        when(departmentService.updateDepartment(eq(1L), any(Department.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDepartment)))
            .andExpect(status().isNotFound());

        verify(departmentService, times(1)).updateDepartment(eq(1L), any(Department.class));
    }

    @Test
    void testDeleteDepartment_Success() throws Exception {
        when(departmentService.deleteDepartment(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }

    @Test
    void testDeleteDepartment_NotFound() throws Exception {
        when(departmentService.deleteDepartment(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }

}