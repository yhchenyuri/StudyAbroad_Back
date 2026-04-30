package com.example.back.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.back.model.Courses;
import com.example.back.repository.CoursesRepository;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CoursesController {
	
    @Autowired
    private CoursesRepository coursesRepository;
	
    @GetMapping("/list") // 建議改成小寫的 list
    public List<Courses> getAllCourses(){
        return coursesRepository.findAll();
    }
	
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCourse(
    		@RequestParam(value = "id", required = false) Integer id,
            @RequestParam("courseName") String courseName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("countryId") Integer countryId,
            
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            
            @RequestParam(value = "endDate", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam("isActive") Boolean isActive,
            @RequestParam(value = "fileData", required = false) MultipartFile fileData) {
            
    	try {
            Courses course;
            
            // 🌟 判斷是新增還是編輯
            if (id != null) {
                // 如果前端有傳 ID 過來，代表是「編輯」，去資料庫把舊資料撈出來
                course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("找不到該課程"));
            } else {
                // 如果沒有 ID，代表是「新增」，產生一個全新的空殼
                course = new Courses();
                course.setCurrentEnrollment(0); 
            }

            
            course.setCourseName(courseName);
            course.setPrice(price);
            course.setCountryId(countryId);
            course.setStartDate(startDate);
            course.setEndDate(endDate);
            course.setRemarks(remarks);
            course.setIsActive(isActive);
            
            // 🌟 只有當業務有選取新圖片時，才蓋掉舊圖片 (編輯時如果不選新圖片，就保留原本的)
            if (fileData != null && !fileData.isEmpty()) {
                course.setImage(fileData.getBytes());
            }

            // 儲存進資料庫 (Spring Data JPA 會自動判斷：有 ID 就 Update，沒 ID 就 Insert)
            coursesRepository.save(course);
            return ResponseEntity.ok("課程儲存成功！");
            
        } catch (IOException e) {
            return ResponseEntity.status(500).body("圖片處理失敗：" + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        coursesRepository.deleteById(id);
        return ResponseEntity.ok("課程已刪除");
    }
   
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCourseImage(@PathVariable Integer id){
        Courses course = coursesRepository.findById(id).orElse(null);
        if (course != null && course.getImage() != null) {
            return ResponseEntity.ok()
                   .contentType(MediaType.IMAGE_JPEG)
                   .body(course.getImage());
        }
        return ResponseEntity.notFound().build();
    }

}