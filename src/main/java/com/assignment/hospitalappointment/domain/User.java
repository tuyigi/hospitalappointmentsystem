package com.assignment.hospitalappointment.domain;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column( name = "last_name")
    private String lastName;
    private String username;
    private String password;
    @CreationTimestamp
    private Date createdAt;
}
