package com.example.back.model;


import com.example.back.constant.StudentsGender;
import com.example.back.converter.GenderConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @PrePersist
//    public void generateUUID() {
//        if (this.uuid == null) {
//            this.uuid = java.util.UUID.randomUUID().toString();
//        }
//    }
    @PrePersist
    public void generateUUID() {
        if (this.uuid == null || this.uuid.isBlank()) {
            this.uuid = java.util.UUID.randomUUID().toString();
        }
    }

//    @Column(name = "uuid", unique = true, nullable = false)
//    private String uuid;

    @Column(name = "uuid", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String uuid;

    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender", columnDefinition = "enum('男','女','其他')")
    private StudentsGender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Country country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "ice_contact")
    private String iceContact;

    @Column(name = "ice_phone")
    private String icePhone;

    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.time.LocalDateTime createdAt;


}