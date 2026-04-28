package com.example.back.repository;

import com.example.back.model.ConsultationRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsultationRecordsRepository extends JpaRepository<ConsultationRecords, Integer> {
    
    // 根據諮詢主檔的 ID 抓取所有的聯繫紀錄，並按照聯絡日期「由新到舊」排序
    // 這會翻譯成 SQL: SELECT * FROM consultation_records WHERE consultations_id = ? ORDER BY contact_date DESC
    List<ConsultationRecords> findByConsultationsId_IdOrderByContactDateDesc(Integer consultationId);
}