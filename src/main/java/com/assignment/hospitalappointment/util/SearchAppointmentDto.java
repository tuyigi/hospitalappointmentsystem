package com.assignment.hospitalappointment.util;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchAppointmentDto {
    private long id;
    private LocalDate date;
}
