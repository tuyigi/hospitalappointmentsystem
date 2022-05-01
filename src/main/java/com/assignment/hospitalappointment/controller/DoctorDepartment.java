package com.assignment.hospitalappointment.controller;


import com.assignment.hospitalappointment.service.DoctorService;
import com.assignment.hospitalappointment.util.DoctorDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorDepartment {
    @Autowired
    DoctorService doctorService;


    // save new doctor

    @PostMapping
    private ResponseEntity<ResponseDto> saveDoctor(@RequestBody DoctorDto doctorDto){
        try{
            return doctorService.createDoctor(doctorDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e), HttpStatus.BAD_REQUEST);
        }
    }

    // get all doctors

    @GetMapping
    private ResponseEntity<ResponseDto> getAllDoctors(){
        try{
            return doctorService.findAllDoctors();
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    // get specific doctor by id

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDto> getSpecificDoctor(@PathVariable long id){
        try{
            return doctorService.detailsDoctor(id);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    // update existing doctor

    @PutMapping("/{id}")
    private ResponseEntity<ResponseDto> updateDoctor(@PathVariable long id, @RequestBody DoctorDto doctorDto){
        try{
            return doctorService.updateDoctor(doctorDto,id);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

}
