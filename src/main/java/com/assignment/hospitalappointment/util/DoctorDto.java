package com.assignment.hospitalappointment.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private EGenderType gender;
    private int work_experience;
    private int day_start_hour;
    private int day_end_hour;
    private long department;
}
