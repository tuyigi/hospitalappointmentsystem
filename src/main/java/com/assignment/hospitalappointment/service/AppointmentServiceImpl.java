package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.domain.Appointment;
import com.assignment.hospitalappointment.domain.Doctor;
import com.assignment.hospitalappointment.repository.AppointmentRepository;
import com.assignment.hospitalappointment.repository.DoctorRepository;
import com.assignment.hospitalappointment.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    // daily appoint schedule of a specific doctor

    @Override
    public ResponseEntity<ResponseDto> getDoctorAppointment(SearchAppointmentDto searchAppointmentDto) {
        try {
        // check if doctor is exist
        Optional<Doctor> doctorExist = doctorRepository.findById(searchAppointmentDto.getId());
        if (!doctorExist.isPresent()) {
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND, "Doctor not found"), HttpStatus.NOT_FOUND);
        }
        Doctor doctor = doctorExist.get();
        // appointment result
        List<AppointmentResultDto> appointmentResults = new ArrayList<>();
        // get all existing appointments on given date and check for availability of doctor
        List<Appointment> takenAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(searchAppointmentDto.getId(), searchAppointmentDto.getDate());
        // check if we appointment
        if (takenAppointments.size() > 0) {

            // get doctor working hours range
            Appointment appointment = null;
            for (int h = doctor.getDayStartHour(); h <= doctor.getDayEndHour(); h++) {
                // check if specific hour is already taken or not
                int finalH = h;
                appointment = takenAppointments.stream().filter(appointment1 -> appointment1.getAppointmentHour() == finalH && appointment1.getStatus().equals(EAppointmentStatus.APPROVED)).findAny().orElse(null);

                if (appointment != null && appointment.getStatus().equals(EAppointmentStatus.APPROVED)) {

                    // taken appointment
                    appointmentResults.add(new AppointmentResultDto(appointment.getPatientNames(), appointment.getPatientPhone(), appointment.getAppointmentHour(), searchAppointmentDto.getDate(), EAppointmentResult.NOT_AVAILABLE));
                } else {
                    // appointment not taken yet
                    appointmentResults.add(new AppointmentResultDto("", "", h, searchAppointmentDto.getDate(), EAppointmentResult.AVAILABLE));
                }
            }
        } else {
            // when no any appointment already taken
            // we loop through all doctor working hours per day
            for (int h = doctor.getDayStartHour(); h <= doctor.getDayEndHour(); h++) {
                appointmentResults.add(new AppointmentResultDto("", "", h, searchAppointmentDto.getDate(), EAppointmentResult.AVAILABLE));
            }
        }
        return new ResponseEntity(new ResponseDto(HttpStatus.OK, "Appoint fetched successful", appointmentResults), HttpStatus.OK);
      }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    // request an appointment

    @Override
    public ResponseEntity<ResponseDto> requestAppointment(AppointmentDto appointmentDto) {

        try{
        // validate if there is not missed parameters
        if(appointmentDto.getPatient_names().isEmpty() || appointmentDto.getPatient_phone().isEmpty() || appointmentDto.getPatient_email().isEmpty()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missing parameters"),HttpStatus.BAD_REQUEST);
        }
        // validate dates
        LocalDate today=LocalDate.now();
        if(appointmentDto.getAppointment_date().compareTo(today)<0){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"INvalid date"),HttpStatus.BAD_REQUEST);
        }

        // validate hour

        if(appointmentDto.getAppointment_hour()>24 || appointmentDto.getAppointment_hour()<=0){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid hour"),HttpStatus.BAD_REQUEST);
        }

        // validate hour , check if hour has not yet passed if date is equal to today
        int nowHour=LocalTime.now().getHour();
        // validate hour
        if(appointmentDto.getAppointment_date().compareTo(today)==0 && nowHour>appointmentDto.getAppointment_hour()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Hour has already passed"),HttpStatus.BAD_REQUEST);
        }
        // check if doctor exists
        Optional<Doctor> doctorExist=doctorRepository.findById(appointmentDto.getDoctor());
        if(!doctorExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,"Doctor not found by this is "+appointmentDto.getDoctor()),HttpStatus.NOT_FOUND);
        }

        // check if the hour is with in the range of doctor working hour

        if(appointmentDto.getAppointment_hour()>doctorExist.get().getDayEndHour() || appointmentDto.getAppointment_hour()<doctorExist.get().getDayStartHour()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Doctor does not work this time (out of range)"),HttpStatus.BAD_REQUEST);
        }

        Appointment appointment=new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointment_date());
        appointment.setAppointmentHour(appointmentDto.getAppointment_hour());
        appointment.setDoctor(doctorExist.get());
        appointment.setPatientEmail(appointmentDto.getPatient_email());
        appointment.setPatientNames(appointmentDto.getPatient_names());
        appointment.setPatientPhone(appointmentDto.getPatient_phone());
        appointment.setStatus(EAppointmentStatus.PENDING);
        // saved appointment
        Appointment savedAppointment=appointmentRepository.save(appointment);
        return new ResponseEntity(new ResponseDto(HttpStatus.CREATED,"Appointment request successul , please wait for approval",savedAppointment),HttpStatus.CREATED);

       }catch (Exception e){
           return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }

    // get all requested appointments

    @Override
    public ResponseEntity<ResponseDto> getAllRequestedAppointments(ReportAppointmentDto reportAppointmentDto) {
        List<Appointment> filteredAppointment=appointmentRepository.findByAppointmentDate(reportAppointmentDto.getDate());
        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"requested appointment fetched successfuk",filteredAppointment),HttpStatus.OK);
    }

    // approve request appointment

    @Override
    public ResponseEntity<ResponseDto> approveAppointmentRequest(long id) {
        try{

        // check if appointment exists
        Optional<Appointment> appointmentExist=appointmentRepository.findById(id);
        if(!appointmentExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,"Appointment request doesn'' exist"),HttpStatus.NOT_FOUND);
        }

        Appointment appointment=appointmentExist.get();

        //check if date and hour have not passed before approve it
        LocalDate today=LocalDate.now();
        if(appointment.getAppointmentDate().compareTo(today)<0){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"INvalid date"),HttpStatus.BAD_REQUEST);
        }

        // validate hour , check if hour has not yet passed if date is equal to today
        int nowHour=LocalTime.now().getHour();
        // validate hour
        if(appointment.getAppointmentDate().compareTo(today)==0 && nowHour>appointment.getAppointmentHour()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Hour has already passed"),HttpStatus.BAD_REQUEST);
        }

        // check if it is not already approved
        if(appointment.getStatus().equals(EAppointmentStatus.APPROVED)){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Appointment is already approved!"),HttpStatus.BAD_REQUEST);
        }

        // we don'' allow more than one approved appointment on the same hour and date on one doctor
        // check if there is no other appointment approved on this hour and date before this appointment
        List<Appointment> oneAppointmentBefore=appointmentRepository.findByAppointmentDateAndDoctorIdAndAndAppointmentHourAndStatus(appointment.getAppointmentDate(),appointment.getDoctor().getId(),appointment.getAppointmentHour(),EAppointmentStatus.APPROVED);
        if(oneAppointmentBefore.size()>=1){
            return  new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"you are not allowed to allow more than one appointment on the same hour"),HttpStatus.BAD_REQUEST);
        }

        appointment.setStatus(EAppointmentStatus.APPROVED);
        appointmentRepository.save(appointment);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"Appointment request approved successful"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e),HttpStatus.BAD_REQUEST);
        }
    }


    // decline request appointment

    @Override
    public ResponseEntity<ResponseDto> declineAppointmentRequest(long id) {
        // check if appointment exists
        Optional<Appointment> appointmentExist=appointmentRepository.findById(id);
        if(!appointmentExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,"Appointmnet doesn't exist"),HttpStatus.NOT_FOUND);
        }
        Appointment appointment=appointmentExist.get();

        // check if it is approved , we can'' decline the approved appointment
        if(appointment.getStatus().equals(EAppointmentStatus.APPROVED)){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"you can not decline this appointment cause it is approved"),HttpStatus.BAD_REQUEST);
        }

        // check if it is already declined
        if(appointment.getStatus().equals(EAppointmentStatus.DECLINED)){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Appointment already declined"),HttpStatus.BAD_REQUEST);
        }

        appointment.setStatus(EAppointmentStatus.DECLINED);
        appointmentRepository.save(appointment);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"Appointment declined successful"),HttpStatus.OK);
    }




}
