package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "consultations")
public class Consultations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 對應資料庫 int

    // 改名為 appointmentsId，並對應 appointments_id 欄位
    @OneToOne
    @JoinColumn(name = "appointments_id")
    private Appointments appointmentsId;

    // 改名為 employeesId，並對應 employees_id 欄位
    
    @ManyToOne 
    @JoinColumn(name = "employees_id")
    private Employees employeesId;

    @Column(name = "status")
    private String status; // 對應 varchar

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt; // 對應 timestamp

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt; // 對應 timestamp

    @Column(name = "latest_next_step")
    private String latestNextStep; // 對應 varchar

    
    @OneToMany(mappedBy = "consultationsId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultationRecords> records = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Appointments getAppointmentsId() {
        return appointmentsId;
    }

    public void setAppointmentsId(Appointments appointmentsId) {
        this.appointmentsId = appointmentsId;
    }

    public Employees getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(Employees employeesId) {
        this.employeesId = employeesId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getLatestNextStep() {
        return latestNextStep;
    }

    public void setLatestNextStep(String latestNextStep) {
        this.latestNextStep = latestNextStep;
    }

    public List<ConsultationRecords> getRecords() {
        return records;
    }

    public void setRecords(List<ConsultationRecords> records) {
        this.records = records;
    }
}