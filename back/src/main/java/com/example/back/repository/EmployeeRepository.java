package com.example.back.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.back.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Optional<Employee> findByEmployeeCode(String employeeCode);
}
