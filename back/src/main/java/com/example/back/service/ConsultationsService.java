package com.example.back.service;

import com.example.back.model.*;
import com.example.back.dto.*;
import com.example.back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationsService {

    @Autowired
    private ConsultationsRepository consultationsRepo;
    @Autowired
    private ConsultationRecordsRepository recordsRepo;
    @Autowired
    private EmployeeRepository employeesRepo;

    public List<Consultations> getAllConsultations() {
        return consultationsRepo.findAll();
    }

    public Consultations getConsultationById(Integer id) {
        return consultationsRepo.findById(id).orElseThrow(() -> new RuntimeException("找不到案件"));
    }

    // ==========================================
    // [Pro級] 新增或修改指派：自動留下變動軌跡
    // ==========================================
    @Transactional
    public void assignConsultant(Integer consultationId, Integer newEmployeeId, String managerName) {
        // 1. 撈出實體
        Consultations consultation = consultationsRepo.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("找不到該筆諮詢案件"));
                
        Employee newEmployee = employeesRepo.findById(newEmployeeId)
                .orElseThrow(() -> new RuntimeException("找不到指定的業務顧問"));

        // 2. 記錄舊的業務名稱 (用於存檔備註)
        String oldEmployeeName = (consultation.getEmployeesId() != null) 
                ? consultation.getEmployeesId().getName() : "尚未指派";

        // 3. 更新主檔狀態與時間
        consultation.setEmployeesId(newEmployee);
        consultation.setAssignedAt(LocalDateTime.now());
        consultation.setStatus("已指派");
        consultation.setLatestNextStep("主管重新分配，等待新顧問聯繫");

        // 4. 自動新增一筆「系統自動紀錄」，這就是細緻感的來源
        ConsultationRecords systemRecord = new ConsultationRecords();
        systemRecord.setConsultationsId(consultation); // 建立關聯
        systemRecord.setContactMethod("系統自動紀錄");
        systemRecord.setResult("案件轉移");
        
        // 運用 Java 17 的文字塊 (Text Blocks) 與 formatted()
        systemRecord.setContent("""
                執行主管： %s 
                操作內容： 將案件由 [%s] 轉移給 [%s]
                """.formatted(managerName, oldEmployeeName, newEmployee.getName()));
        
        systemRecord.setNextStep("等待新顧問聯繫學生");
        systemRecord.setContactDate(LocalDateTime.now());

        // 5. 同步儲存
        recordsRepo.save(systemRecord);
        consultationsRepo.save(consultation);
    }

    // 刪除案件 (包含級聯刪除)
    @Transactional
    public void deleteConsultation(Integer id) {
        consultationsRepo.deleteById(id);
    }

    // 業務手動新增聯繫紀錄
    @Transactional
    public void addTrackingRecord(RecordRequestDto request) {
        Consultations consultation = getConsultationById(request.consultationId());
        
        ConsultationRecords record = new ConsultationRecords();
        record.setConsultationsId(consultation);
        record.setContactMethod(request.contactMethod());
        record.setContent(request.content());
        record.setResult(request.result());
        record.setNextStep(request.nextStep());
        record.setContactDate(LocalDateTime.now());
        
        recordsRepo.save(record);

        // 同步更新主檔狀態
        consultation.setStatus(request.result());
        consultation.setLatestNextStep(request.nextStep());
        consultationsRepo.save(consultation);
    }
}