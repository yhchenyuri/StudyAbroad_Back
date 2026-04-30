package com.example.back.repository;

import com.example.back.model.Consultations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsultationsRepository extends JpaRepository<Consultations, Integer> {

    // 【重要魔法】因為你在 Consultations 裡宣告的變數是：private Employees employeesId;
    // 如果你想用「業務的 Integer ID」來當搜尋條件，必須加上 _Id 來告訴系統往下找
    // 這會翻譯成 SQL: SELECT * FROM consultations WHERE employees_id = ?
    List<Consultations> findByEmployeesId_Id(Integer employeeId);
    
    // 查詢特定狀態的案件 (例如：找所有 "聯繫中" 的案件)
    // 這會翻譯成 SQL: SELECT * FROM consultations WHERE status = ?
    List<Consultations> findByStatus(String status);
}