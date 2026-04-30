/* package com.example.back.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Orders {

	/*    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    // 關聯學生 (students_id)
    @ManyToOne
    @JoinColumn(name = "students_id", nullable = false)
    private Students student;

    // 關聯課程 (courses_id)
    @ManyToOne
    @JoinColumn(name = "courses_id", nullable = false)
    private Courses course;

    // 關聯負責員工/業務 (employees_id)
    @ManyToOne
    @JoinColumn(name = "employees_id")
    private Employees employee;

    @Column(name = "agency_fee", precision = 10, scale = 2)
    private BigDecimal agencyFee;

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private BigDecimal discountRate;

    @Column(name = "final_fee", precision = 10, scale = 2)
    private BigDecimal finalFee;

    @Column(name = "order_status")
    private String orderStatus; // 對應 ENUM('待付款', '已完款')

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    // ==========================================
    // 無參數建構子
    // ==========================================
    public Orders() {
    }

    // ==========================================
    // Getters 與 Setters
    // ==========================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public BigDecimal getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(BigDecimal agencyFee) {
        this.agencyFee = agencyFee;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getFinalFee() {
        return finalFee;
    }

    public void setFinalFee(BigDecimal finalFee) {
        this.finalFee = finalFee;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}*/