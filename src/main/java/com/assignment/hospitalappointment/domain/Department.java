package com.assignment.hospitalappointment.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    long id;
    private String name;
    private String abbreviation;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}