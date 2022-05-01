package com.assignment.hospitalappointment.repository;

import com.assignment.hospitalappointment.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface DepartmentRespository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameOrAbbreviation(String name,String abbreviation);
}
