package com.example.back.controller;

import com.example.back.model.Consultations;
import com.example.back.dto.ConsultantWorkloadDto;
import com.example.back.dto.AssignRequestDto;
import com.example.back.dto.RecordRequestDto;
import com.example.back.service.ConsultationsService;
import com.example.back.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 諮詢與案件管理 REST API 控制器
 * 負責提供 JSON 資料給前端 AngularJS 進行畫面渲染與互動
 */
@RestController
@RequestMapping("/api/consultations")
public class ConsultationsController {

    @Autowired
    private ConsultationsService consultationsService;

    @Autowired
    private EmployeesRepository employeesRepo;

    // ==========================================
    // 讀取功能 (GET)
    // ==========================================

    /**
     * 1. 取得所有案件列表 (案件管理總表使用)
     * 網址：GET /api/consultations/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<Consultations>> getAllConsultations() {
        List<Consultations> list = consultationsService.getAllConsultations();
        return ResponseEntity.ok(list);
    }

    /**
     * 2. 取得特定案件的詳細資料與歷史紀錄 (詳細追蹤頁使用)
     * 網址：GET /api/consultations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Consultations> getConsultationById(@PathVariable("id") Integer id) {
        Consultations consultation = consultationsService.getConsultationById(id);
        return ResponseEntity.ok(consultation);
    }

    /**
     * 3. 取得業務顧問名單及其目前的案件負荷量 (指派下拉選單使用)
     * 網址：GET /api/consultations/workload
     */
    @GetMapping("/workload")
    public ResponseEntity<List<ConsultantWorkloadDto>> getConsultantsWorkload() {
        List<ConsultantWorkloadDto> workloads = employeesRepo.findConsultantsWithWorkload();
        return ResponseEntity.ok(workloads);
    }

    // ==========================================
    // 寫入、更新與刪除功能 (POST / DELETE)
    // ==========================================

    /**
     * 4. [Pro級] 執行案件指派/改派 (並留下系統變更軌跡)
     * 網址：POST /api/consultations/assign
     */
    @PostMapping("/assign")
    public ResponseEntity<Map<String, String>> assignConsultant(@RequestBody AssignRequestDto request) {
        // 將 DTO 中的 managerName 也一併傳給 Service 進行紀錄
        consultationsService.assignConsultant(
            request.consultationId(), 
            request.employeeId(), 
            request.managerName()
        );
        return ResponseEntity.ok(Map.of("message", "案件指派成功，已自動生成系統移交紀錄"));
    }

    /**
     * 5. 新增一筆諮詢聯繫紀錄 (業務回報進度使用)
     * 網址：POST /api/consultations/addRecord
     */
    @PostMapping("/addRecord")
    public ResponseEntity<Map<String, String>> addTrackingRecord(@RequestBody RecordRequestDto request) {
        consultationsService.addTrackingRecord(request);
        return ResponseEntity.ok(Map.of("message", "聯繫紀錄已更新"));
    }

    /**
     * 6. [主管功能] 永久刪除整筆諮詢案件
     * 網址：DELETE /api/consultations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteConsultation(@PathVariable("id") Integer id) {
        consultationsService.deleteConsultation(id);
        return ResponseEntity.ok(Map.of("message", "案件及其所有聯繫明細已永久刪除"));
    }
}