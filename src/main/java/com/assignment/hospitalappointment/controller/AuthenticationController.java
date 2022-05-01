package com.assignment.hospitalappointment.controller;

import com.assignment.hospitalappointment.service.UserService;
import com.assignment.hospitalappointment.util.LoginDto;
import com.assignment.hospitalappointment.util.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    private ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto){
        try{
            return userService.login(loginDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e), HttpStatus.BAD_REQUEST);
        }
    }
}
