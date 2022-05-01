package com.assignment.hospitalappointment.domain;

import com.assignment.hospitalappointment.util.EAppointmentStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "patient_names")
    private String patientNames;
    @Column(name="patient_email")
    private String patientEmail;
    @Column(name="patient_phone")
    private String patientPhone;
    @Column(name = "appointment_hour")
    private int appointmentHour;
    @Column(name = "appointment_date")
    private LocalDate appointmentDate;
    @Enumerated(EnumType.STRING)
    private EAppointmentStatus status;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date upadtedAt;

}
