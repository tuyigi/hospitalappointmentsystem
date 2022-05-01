package com.assignment.hospitalappointment.service;

import com.assignment.hospitalappointment.domain.Department;
import com.assignment.hospitalappointment.domain.Doctor;
import com.assignment.hospitalappointment.repository.DepartmentRespository;
import com.assignment.hospitalappointment.repository.DoctorRepository;
import com.assignment.hospitalappointment.util.DoctorDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import com.assignment.hospitalappointment.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    Validation validation;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DepartmentRespository departmentRespository;


    // create new doctor

    @Override
    public ResponseEntity<ResponseDto> createDoctor(DoctorDto doctorDto) {
        // check if no data is missed
        if(doctorDto.getEmail().isEmpty() || doctorDto.getLast_name().isEmpty() || doctorDto.getFirst_name().isEmpty() || doctorDto.getPhone_number().isEmpty()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missing parameters"),HttpStatus.BAD_REQUEST);
        }

        // if phone number is valid and has only 10 digits
        if(doctorDto.getPhone_number().length()!=10){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid phone number"),HttpStatus.BAD_REQUEST);
        }

        // check if email is valid
        Pattern pattern = Pattern.compile(validation.getRegexEmail());
        Matcher matcher = pattern.matcher(doctorDto.getEmail());
        if(!matcher.matches()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid email"),HttpStatus.BAD_REQUEST);
        }

        // check if working hours are valid
        if(doctorDto.getDay_start_hour()<=0 || doctorDto.getDay_start_hour()> doctorDto.getDay_end_hour() || doctorDto.getDay_end_hour()>24){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid working hour"),HttpStatus.BAD_REQUEST);
        }

        // check if email is not already taken by other doctor
        Optional<Doctor> doctorEmailExist=doctorRepository.findByEmail(doctorDto.getEmail());
        if(doctorEmailExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,doctorDto.getEmail()+" Email already used by other doctor"),HttpStatus.BAD_REQUEST);
        }

        // check if phone is not already used by other doctor
        Optional<Doctor> doctorPhoneExist=doctorRepository.findByPhoneNumber(doctorDto.getPhone_number());
        if(doctorPhoneExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,doctorDto.getPhone_number()+" phone number already used by other doctor "),HttpStatus.BAD_REQUEST);
        }

        // valid if department exists
        Optional<Department> departmentExist=departmentRespository.findById(doctorDto.getDepartment());
        if(!departmentExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,doctorDto.getDepartment()+" department does not exist"),HttpStatus.NOT_FOUND);
        }

        // get department
        Department department=departmentExist.get();

        // save new doctor
        Doctor doctor=new Doctor();
        doctor.setDayEndHour(doctorDto.getDay_end_hour());
        doctor.setDayStartHour(doctorDto.getDay_start_hour());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setGender(doctorDto.getGender());
        doctor.setFirstName(doctorDto.getFirst_name());
        doctor.setLastName(doctorDto.getLast_name());
        doctor.setPhoneNumber(doctorDto.getPhone_number());
        doctor.setWorkExperience(doctorDto.getWork_experience());
        doctor.setDepartment(department);
        Doctor savedDoctor=doctorRepository.save(doctor);
        return new ResponseEntity(new ResponseDto(HttpStatus.CREATED, "Doctor created successfuly",savedDoctor),HttpStatus.CREATED);
    }

    // update doctor details

    @Override
    public ResponseEntity<ResponseDto> updateDoctor(DoctorDto doctorDto, long id) {
        // check if doctor already exist
        Optional<Doctor> doctorExist=doctorRepository.findById(id);
        if(!doctorExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,"Doctor not found by this id "+id),HttpStatus.NOT_FOUND);
        }


        if(doctorDto.getEmail().isEmpty() || doctorDto.getLast_name().isEmpty() || doctorDto.getFirst_name().isEmpty() || doctorDto.getPhone_number().isEmpty()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Missing parameters"),HttpStatus.BAD_REQUEST);
        }

        // if phone number is valid and has only 10 digits
        if(doctorDto.getPhone_number().length()!=10){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid phone number"),HttpStatus.BAD_REQUEST);
        }

        // check if email is valid
        Pattern pattern = Pattern.compile(validation.getRegexEmail());
        Matcher matcher = pattern.matcher(doctorDto.getEmail());
        if(!matcher.matches()){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid email"),HttpStatus.BAD_REQUEST);
        }

        // check if working hours are valid
        if(doctorDto.getDay_start_hour()<=0 || doctorDto.getDay_start_hour()> doctorDto.getDay_end_hour() || doctorDto.getDay_end_hour()>24){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,"Invalid working hour"),HttpStatus.BAD_REQUEST);
        }

        // check if email is not already taken by other doctor
        Optional<Doctor> doctorEmailExist=doctorRepository.findByEmail(doctorDto.getEmail());
        if(doctorEmailExist.isPresent() && doctorEmailExist.get().getId()!=id){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,doctorDto.getEmail()+" Email already used by other doctor"),HttpStatus.BAD_REQUEST);
        }

        // check if phone is not already used by other doctor
        Optional<Doctor> doctorPhoneExist=doctorRepository.findByPhoneNumber(doctorDto.getPhone_number());
        if(doctorPhoneExist.isPresent() && doctorEmailExist.get().getId()!=id){
            return new ResponseEntity(new ResponseDto(HttpStatus.BAD_REQUEST,doctorDto.getPhone_number()+" phone number already used by other doctor "),HttpStatus.BAD_REQUEST);
        }

        // valid if department exists
        Optional<Department> departmentExist=departmentRespository.findById(doctorDto.getDepartment());
        if(!departmentExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,doctorDto.getDepartment()+" department does not exist"),HttpStatus.NOT_FOUND);
        }

        // get department
        Department department=departmentExist.get();
        Doctor doctor=doctorExist.get();
        doctor.setDayEndHour(doctorDto.getDay_end_hour());
        doctor.setDayStartHour(doctorDto.getDay_start_hour());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setGender(doctorDto.getGender());
        doctor.setFirstName(doctorDto.getFirst_name());
        doctor.setLastName(doctorDto.getLast_name());
        doctor.setPhoneNumber(doctorDto.getPhone_number());
        doctor.setWorkExperience(doctorDto.getWork_experience());
        doctor.setDepartment(department);
        Doctor updatedDoctor=doctorRepository.save(doctor);
        return new ResponseEntity(new ResponseDto(HttpStatus.OK, "Doctor created successfuly",updatedDoctor),HttpStatus.OK);

    }

    // get doctor details

    @Override
    public ResponseEntity<ResponseDto> detailsDoctor(long id) {
        // check if doctor already exist
        Optional<Doctor> doctorExist=doctorRepository.findById(id);
        if(!doctorExist.isPresent()){
            return new ResponseEntity(new ResponseDto(HttpStatus.NOT_FOUND,"Doctor not found by this id "+id),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"doctor details found",doctorExist.get()),HttpStatus.OK);
    }

    // get All doctors

    @Override
    public ResponseEntity<ResponseDto> findAllDoctors() {
        return new ResponseEntity(new ResponseDto(HttpStatus.OK,"Doctors fetched successfuly",doctorRepository.findAll()),HttpStatus.OK);
    }
}
