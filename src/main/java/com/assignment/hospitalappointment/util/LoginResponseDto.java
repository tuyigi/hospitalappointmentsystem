package com.assignment.hospitalappointment.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private long id;
    private String token;
    private String first_name;
    private String last_name;
}
