//package com.example.back.controller;
//
//import com.example.back.model.Students;
//import com.example.back.repository.StudentsRepository;
////import com.example.back.repository.CountryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@Controller
////public class StudentsController {
////
////    @Autowired
////    private StudentsRepository studentsRepository;
////
////    @GetMapping("/admin/students")
////    public String studentList(Model model) {
////        List<Students> students = studentsRepository.findAll();
////        model.addAttribute("students", students);
////        return "/admin/students"; // ← 你的 html 檔名
////    }
////
////}
//
//@Controller
//public class StudentsController {
//     @Autowired
//     private StudentsRepository studentsRepository;
//
//     @GetMapping("/admin/students")
//     public String list(Model model) {
//         List<Students> list = studentsRepository.findAll();
//         System.out.println(list);
//         model.addAttribute("students", list);
//         return "students";
//    }
//    @GetMapping("/admin/students/{id}")
//    @ResponseBody
//    public Students getStudentById(@PathVariable Integer id) {
//        return studentsRepository.findById(id).orElse(null);
//    }
//}
package com.example.back.controller;

import com.example.back.model.Students;
import com.example.back.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/students")
public class StudentsController {

    private final StudentsRepository studentsRepository;

    /**
     * 📌 列表頁：顯示所有學生
     */
    @GetMapping
    public String list(Model model) {

        List<Students> students = studentsRepository.findAll();

        model.addAttribute("students", students);

        // ⭐ 防呆：避免 Thymeleaf 單一 student null（如果你頁面還有用）
        model.addAttribute("student", new Students());

        return "students"; // templates/students.html
    }

    /**
     * 📌 單一學生 API（給 modal / AJAX 用）
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Students getStudentById(@PathVariable Integer id) {

        return studentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

}
