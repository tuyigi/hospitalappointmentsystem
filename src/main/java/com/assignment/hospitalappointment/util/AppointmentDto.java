package com.assignment.hospitalappointment.util;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    private String patient_names;
    private String patient_email;
    private String patient_phone;
    private int appointment_hour;
    private LocalDate appointment_date;
    private long doctor;
}