package com.assignment.hospitalappointment.controller;

import com.assignment.hospitalappointment.service.UserService;
import com.assignment.hospitalappointment.util.ResponseDto;
import com.assignment.hospitalappointment.util.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    private ResponseEntity<ResponseDto> createUser(@RequestBody UserCreationDto userCreationDto){
        try{
            return userService.createUser(userCreationDto);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDto(e), HttpStatus.BAD_REQUEST);
        }
    }
}
