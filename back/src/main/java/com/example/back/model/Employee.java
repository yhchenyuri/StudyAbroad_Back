package com.example.back.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="employees")
@Data

public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_code")
    private String employeeCode;

    private String email;
    private String password;
    private String role;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    private String name;
    private String phone;
    private String department;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

}
