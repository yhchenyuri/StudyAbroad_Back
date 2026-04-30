package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// 引入 JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "consultation_records")
public class ConsultationRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 對應資料庫 int

    // 變數名稱對齊 consultations_id，並精準映射到資料庫的外鍵欄位
    @JsonIgnore // 🌟 加上這行，阻斷 JSON 轉換時回頭去查主表的無限迴圈
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultations_id", nullable = false)
    private Consultations consultationsId;

    @Column(name = "contact_date", nullable = false)
    private LocalDateTime contactDate; // 對應 timestamp

    @Column(name = "contact_method")
    private String contactMethod; // 對應 varchar

    @Column(columnDefinition = "TEXT")
    private String content; // 對應 text，通常內容較長

    @Column(name = "result")
    private String result; // 對應 varchar

    @Column(name = "next_step")
    private String nextStep; // 對應 varchar

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 對應 timestamp

    // 當這筆紀錄「第一次」被存入資料庫時，自動押上當前時間
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- 以下為 Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Consultations getConsultationsId() {
        return consultationsId;
    }

    public void setConsultationsId(Consultations consultationsId) {
        this.consultationsId = consultationsId;
    }

    public LocalDateTime getContactDate() {
        return contactDate;
    }

    public void setContactDate(LocalDateTime contactDate) {
        this.contactDate = contactDate;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}