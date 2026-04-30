package com.example.back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="courses")
@Data
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "current_enrollment")
    private Integer currentEnrollment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration_days", insertable = false, updatable = false)
    private Integer durationDays;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Lob
    @Column(name="image",columnDefinition = "LONGBLOB")
    private byte[]image;
    

}
