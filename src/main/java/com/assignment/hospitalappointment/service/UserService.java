package com.assignment.hospitalappointment.service;


import com.assignment.hospitalappointment.util.LoginDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import com.assignment.hospitalappointment.util.UserCreationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    ResponseEntity<ResponseDto> createUser(UserCreationDto userCreationDto);
    ResponseEntity<ResponseDto> login(LoginDto loginDto);
    UserDetails loadUserByUsername(String username);
}
