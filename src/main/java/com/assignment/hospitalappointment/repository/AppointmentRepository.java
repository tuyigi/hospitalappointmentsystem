package com.assignment.hospitalappointment.repository;

import com.assignment.hospitalappointment.domain.Appointment;
import com.assignment.hospitalappointment.util.EAppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment>  findByDoctorIdAndAppointmentDate(long id, LocalDate date);
    Optional<Appointment> findByDoctorIdAndAppointmentDateAndAppointmentHourAndStatus(long id, LocalDate date, int appointmentHour, EAppointmentStatus status);
    List<Appointment> findByAppointmentDateAndDoctorIdAndAndAppointmentHourAndStatus( LocalDate date,long id, int appointmentHour, EAppointmentStatus status);
    List<Appointment>  findByAppointmentDate(LocalDate date);
}
