/*package com.example.back.service;

//import com.example.back.model.Orders;
//import com.example.back.model.Courses;
import com.example.back.repository.OrdersRepository;
//import com.example.back.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {

/*   @Autowired
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

   
    @Transactional
    public Orders saveOrder(Orders order) {
        if (order.getUuid() == null || order.getUuid().isEmpty()) {
            order.setUuid(UUID.randomUUID().toString());
        }
        return ordersRepo.save(order);
    }
}*/