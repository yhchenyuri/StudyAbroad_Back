package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 對應 id (PK, int)

    @Column(name = "employee_code")
    private String employeeCode; // 對應 employee_code (雖然圖中是1,2，但員編通常建議用 String 防呆，若你確定是 int 也可以改成 Integer)

    @Column(name = "name")
    private String name; // 對應 name (varchar)

    @Column(name = "email")
    private String email; // 對應 email (varchar)

    @Column(name = "password")
    private String password; // 對應 password (varchar)

    @Column(name = "department")
    private String department; // 對應 department (varchar)

    @Column(name = "role")
    private String role; // 對應 role (varchar)

    @Column(name = "phone")
    private String phone; // 對應 phone (varchar)

    // 圖中的 is_active 顯示為 1，通常在 MySQL 是 tinyint，在 Java 中用 Integer 接收最為精準
    @Column(name = "is_active")
    private Integer isActive; 



    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 對應 created_at (timestamp)

    // 新增員工時，自動押上建立時間
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = 1; // 預設為啟用狀態
        }
    }

    // --- 以下為 Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 