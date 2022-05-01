package com.assignment.hospitalappointment.controller;

import com.assignment.hospitalappointment.service.AppointmentService;
import com.assignment.hospitalappointment.util.AppointmentDto;
import com.assignment.hospitalappointment.util.ReportAppointmentDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import com.assignment.hospitalappointment.util.SearchAppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    private ResponseEntity<ResponseDto> getAppointment(@RequestBody SearchAppointmentDto searchAppointmentDto){
        try{
            return appointmentService.getDoctorAppointment(searchAppointmentDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/request")
    private ResponseEntity<ResponseDto> requestAppointment(@RequestBody AppointmentDto appointmentDto){
        try {
            return appointmentService.requestAppointment(appointmentDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/report")
    private ResponseEntity<ResponseDto> getRequestedAppointment(@RequestBody ReportAppointmentDto reportAppointmentDto){
        try{

            return appointmentService.getAllRequestedAppointments(reportAppointmentDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/approve/{id}")
    private ResponseEntity<ResponseDto> approveAppointmentRequest(@PathVariable long id){
        try{
            return appointmentService.approveAppointmentRequest(id);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/decline/{id}")
    private ResponseEntity<ResponseDto> declineAppointmentRequest(@PathVariable long id){
        try{
            return appointmentService.declineAppointmentRequest(id);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

}
