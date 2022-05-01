package com.assignment.hospitalappointment.util;

import lombok.Data;

@Data
public class UserCreationDto {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
}
