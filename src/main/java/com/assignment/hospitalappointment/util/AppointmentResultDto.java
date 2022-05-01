package com.assignment.hospitalappointment.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AppointmentResultDto {
    private String patient_names;
    private String phone_number;
    private int hour;
    private LocalDate date;
    private EAppointmentResult status;
}
