package com.assignment.hospitalappointment.controller;


import com.assignment.hospitalappointment.service.DepartmentService;
import com.assignment.hospitalappointment.util.DepartmentDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping
    private ResponseEntity<ResponseDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        try{
            return  departmentService.createDepartment(departmentDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    private ResponseEntity<ResponseDto> getAllDepartments(){
        try{
            return departmentService.getAllDepartments();
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }


}
