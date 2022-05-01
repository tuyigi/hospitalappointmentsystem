package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.util.AppointmentDto;
import com.assignment.hospitalappointment.util.ReportAppointmentDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import com.assignment.hospitalappointment.util.SearchAppointmentDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;


public interface AppointmentService {
    ResponseEntity<ResponseDto> getDoctorAppointment(SearchAppointmentDto searchAppointmentDto);
    ResponseEntity<ResponseDto> requestAppointment(AppointmentDto appointmentDto);
    ResponseEntity<ResponseDto> getAllRequestedAppointments(ReportAppointmentDto reportAppointmentDto);
    ResponseEntity<ResponseDto> approveAppointmentRequest(long id);
    ResponseEntity<ResponseDto> declineAppointmentRequest(long id);
}
