package com.example.back.repository;

import com.example.back.dto.ConsultantWorkloadDto;
import com.example.back.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    // ==========================================
    // 1. [登入系統用] 透過帳號尋找員工
    // ==========================================
    // 使用 Optional 是一個非常棒的 Java 現代化寫法 (防範 NullPointerException)
    // 這樣在 Service 驗證密碼時，就可以優雅地處理「帳號不存在」的情況
    
    Optional<Employees> findByName(String name);

    // ==========================================
    // 2. [分案儀表板用] 透過角色權限篩選名單
    // ==========================================
    // 當主管在分配案件時，下拉選單不應該出現其他主管或會計，只能出現「業務顧問」
    // 這會翻譯成 SQL: SELECT * FROM employees WHERE role = ?
    List<Employees> findByRole(String role);
    
    /**
     * 撈取所有業務顧問，並計算他們目前「未結案」的案件數量
     * 使用 LEFT JOIN 確保就算該業務目前沒有案件 (COUNT 為 0)，也會出現在選單中
     */
    @Query("""
            SELECT new com.example.back.dto.ConsultantWorkloadDto(
                e.id, 
                e.name, 
                COUNT(c.id)
            )
            FROM Employees e
            LEFT JOIN Consultations c ON c.employeesId = e AND c.status NOT IN ('已成交', '暫不考慮 (結案)', '暫緩處理')
            WHERE e.role != 'manager' AND e.role != 'Manager' 
            GROUP BY e.id, e.name
            ORDER BY COUNT(c.id) ASC
        """)
        List<ConsultantWorkloadDto> findConsultantsWithWorkload();
}