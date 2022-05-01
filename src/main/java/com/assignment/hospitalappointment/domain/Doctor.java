package com.assignment.hospitalappointment.domain;


import com.assignment.hospitalappointment.util.EGenderType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "doctor")

public class Doctor {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private EGenderType gender;
    @Column(name = "work_experience")
    private int workExperience;
    @Column(name = "day_start_hour")
    private int dayStartHour;
    @Column(name ="day_end_hour")
    private int dayEndHour;
    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @Column(name = "user_id")
//    private User createdBy;
}