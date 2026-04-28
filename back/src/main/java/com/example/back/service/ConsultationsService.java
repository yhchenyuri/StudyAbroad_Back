package com.example.back.service;

import com.example.back.model.Consultations;
import com.example.back.model.ConsultationRecords;
import com.example.back.model.Employees;
import com.example.back.repository.ConsultationsRepository;
import com.example.back.repository.ConsultationRecordsRepository;
import com.example.back.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // 記得這裡是 jakarta

import java.time.LocalDateTime;

@Service
public class ConsultationsService {

    @Autowired
    private ConsultationsRepository consultationsRepo;
    
    @Autowired
    private EmployeesRepository employeesRepo;
    
    @Autowired
    private ConsultationRecordsRepository recordsRepo;

    // ==========================================
    // 1. [主管功能] 新增或修改指派的業務顧問
    // ==========================================
    @Transactional
    public void assignConsultant(Integer consultationId, Integer newEmployeeId, String managerName) {
        // 1. 撈出該筆諮詢案件與新業務的實體
        Consultations consultation = consultationsRepo.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("找不到該筆諮詢案件"));
                
        Employees newEmployee = employeesRepo.findById(newEmployeeId)
                .orElseThrow(() -> new RuntimeException("找不到指定的業務顧問"));

        // 2. 記錄舊的業務名稱 (如果有才記錄，用於備註)
        String oldEmployeeName = (consultation.getEmployeesId() != null) 
                ? consultation.getEmployeesId().getName() : "無"; // 假設 Employees 裡有 getName()

        // 3. 更新主檔的指派資訊
        consultation.setEmployeesId(newEmployee);
        consultation.setAssignedAt(LocalDateTime.now());
        consultation.setStatus("已指派");
        consultation.setLatestNextStep("主管重新分配，等待新顧問聯繫");

        // 4. [Pro級設計] 自動新增一筆系統明細，留下軌跡
        ConsultationRecords systemRecord = new ConsultationRecords();
        systemRecord.setConsultationsId(consultation);
        systemRecord.setContactMethod("系統自動紀錄");
        systemRecord.setResult("案件轉移");
        systemRecord.setContent("""
                主管 %s 將案件由 [%s] 轉移給 [%s]
                """.formatted(managerName, oldEmployeeName, newEmployee.getName())); // 運用 JDK 17 多行字串與格式化
        systemRecord.setNextStep("等待新顧問聯繫");
        
        // 5. 儲存明細並更新主檔
        recordsRepo.save(systemRecord);
        consultationsRepo.save(consultation);
    }

    // ==========================================
    // 2. [主管功能] 刪除整筆諮詢案件
    // ==========================================
    @Transactional
    public void deleteConsultation(Integer consultationId) {
        // 檢查案件是否存在
        if (!consultationsRepo.existsById(consultationId)) {
            throw new RuntimeException("找不到該筆諮詢案件，無法刪除");
        }
        
        // 直接刪除主檔
        consultationsRepo.deleteById(consultationId);
    }
}