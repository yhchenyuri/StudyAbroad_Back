package com.example.back.repository;

import com.example.back.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {
    boolean existsByEmail(String email);
    Students findByEmail(String email);
}

//只要繼承 JpaRepository，你就直接有：
//save(employee)        // 新增或更新
//findById(id)          // 查一筆
//findAll()             // 查全部
//deleteById(id)        // 刪除
//count()               // 計數
