package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.util.DepartmentDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<ResponseDto> createDepartment(DepartmentDto departmentDto);
    ResponseEntity<ResponseDto> getAllDepartments();
}
