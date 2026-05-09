package com.example.back.service;

import com.example.back.model.Orders;
import com.example.back.model.Courses;
import com.example.back.repository.CoursesRepository;
import com.example.back.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private CoursesRepository coursesRepo; // 用於取得課程下拉選單資料

    
    public List<Orders> getAllOrders() {
        return ordersRepo.findAll();
    }

   
    public List<Courses> getAllCourses() {
        return coursesRepo.findAll();
    }

  
    @Transactional
    public void deleteOrder(Integer id) {
        if (ordersRepo.existsById(id)) {
            ordersRepo.deleteById(id);
        } else {
            throw new RuntimeException("找不到編號為 " + id + " 的訂單，無法刪除");
        }
    }

    // 🌟 新增訂單 (包含自動產生 UUID)
    @Transactional
    public void createOrder(Orders order) {
        if (order.getUuid() == null || order.getUuid().isEmpty()) {
            order.setUuid(UUID.randomUUID().toString());
        }
        ordersRepo.save(order);
    }

    // 🌟 更新訂單
    @Transactional
    public void updateOrder(Integer id, Orders orderDetails) {
        // 1. 先從資料庫找出原有的訂單
        Orders existingOrder = ordersRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("找不到該筆訂單"));

        // 2. 依照你 Orders.java 的標準 Setter 更新欄位資料
        existingOrder.setStudent(orderDetails.getStudent());
        existingOrder.setCourse(orderDetails.getCourse());
        existingOrder.setEmployee(orderDetails.getEmployee());
        existingOrder.setAgencyFee(orderDetails.getAgencyFee());
        existingOrder.setDiscountRate(orderDetails.getDiscountRate());
        existingOrder.setFinalFee(orderDetails.getFinalFee());
        existingOrder.setOrderStatus(orderDetails.getOrderStatus());

        // 3. 儲存修改
        ordersRepo.save(existingOrder);
    }
}