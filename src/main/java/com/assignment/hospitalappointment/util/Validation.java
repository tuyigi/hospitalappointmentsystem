package com.assignment.hospitalappointment.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Validation {
    // pattern for validating email
    @Value("${validation.email}")
    String regexEmail = "^(.+)@(.+)$";
}
