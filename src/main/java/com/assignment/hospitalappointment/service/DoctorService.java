package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.util.DoctorDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.http.ResponseEntity;


public interface DoctorService {
    ResponseEntity<ResponseDto> createDoctor(DoctorDto doctorDto);
    ResponseEntity<ResponseDto> updateDoctor(DoctorDto doctorDto, long id);
    ResponseEntity<ResponseDto> detailsDoctor(long id);
    ResponseEntity<ResponseDto> findAllDoctors();

}
