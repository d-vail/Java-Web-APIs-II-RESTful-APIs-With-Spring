package com.lambdaschool.restfulemps;

import org.springframework.data.jpa.repository.JpaRepository;

// Helps connect Java objects to database
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
