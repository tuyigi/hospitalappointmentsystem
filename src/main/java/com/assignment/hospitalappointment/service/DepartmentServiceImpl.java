package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.domain.Department;
import com.assignment.hospitalappointment.repository.DepartmentRespository;
import com.assignment.hospitalappointment.util.DepartmentDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRespository departmentRespository;

    // create new department

    @Override
    public ResponseEntity<ResponseDto> createDepartment(DepartmentDto departmentDto) {
        // check if all data are not missed
        if(departmentDto.getName().isEmpty() || departmentDto.getAbbreviation().isEmpty()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missing parameters"),HttpStatus.BAD_REQUEST);
        }

        // check if department name or abbreviation is already taken
        Optional<Department> departmentExist=departmentRespository.findByNameOrAbbreviation(departmentDto.getName(),departmentDto.getAbbreviation());
        if(departmentExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Alreday exists"),HttpStatus.BAD_REQUEST);
        }

        Department department=new Department();
        department.setName(departmentDto.getName());
        department.setAbbreviation(departmentDto.getAbbreviation());
        Department savedDepartment=departmentRespository.save(department);
        return new ResponseEntity(new ResponseDto(HttpStatus.CREATED,"Department created successful",savedDepartment),HttpStatus.CREATED);
    }


    // get all departments

    @Override
    public ResponseEntity<ResponseDto> getAllDepartments() {

        List<Department> departments=departmentRespository.findAll();

        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"Departments fetched successful",departments),HttpStatus.OK);
    }
}
