/* package com.example.back.controller;

import com.example.back.model.Orders;
//import com.example.back.model.Courses;
import com.example.back.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

	   @Autowired
    private OrdersService ordersService;

     
    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> list = ordersService.getAllOrders();
        return ResponseEntity.ok(list);
    }

    
    @GetMapping("/courses")
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = ordersService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

     
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Integer id) {
        try {
            ordersService.deleteOrder(id);
            // 回傳一個簡單的 JSON 訊息讓前端知道成功了
            return ResponseEntity.ok(Map.of("message", "訂單已成功刪除！"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "刪除失敗：" + e.getMessage()));
        }
    }
}*/