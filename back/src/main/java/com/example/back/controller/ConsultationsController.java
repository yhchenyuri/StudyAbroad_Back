package com.example.back.controller;

import com.example.back.service.ConsultationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consultations")
public class ConsultationsController {

    @Autowired
    private ConsultationsService consultationsService;

    // 接收前端表單的「變更指派」請求
    @PostMapping("/{id}/assign")
    public String assignConsultant(
            @PathVariable("id") Integer consultationId,
            @RequestParam("employeeId") Integer employeeId,
            RedirectAttributes redirectAttributes) {
        
        try {
            // 這裡的 "主管A" 實務上會從 Session 或 Security Context 中取得目前登入者的姓名
            consultationsService.assignConsultant(consultationId, employeeId, "主管A");
            redirectAttributes.addFlashAttribute("successMsg", "案件指派成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "指派失敗：" + e.getMessage());
        }
        
        // 指派完成後，導回分配儀表板或清單頁
        return "redirect:/consultations/assign"; 
    }

    // 接收前端的「刪除」請求
    @PostMapping("/{id}/delete")
    public String deleteConsultation(
            @PathVariable("id") Integer consultationId,
            RedirectAttributes redirectAttributes) {
        
        try {
            consultationsService.deleteConsultation(consultationId);
            redirectAttributes.addFlashAttribute("successMsg", "案件已成功刪除！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "刪除失敗：" + e.getMessage());
        }
        
        return "redirect:/consultations/assign";
    }
}