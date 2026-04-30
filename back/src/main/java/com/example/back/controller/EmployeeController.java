package com.example.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.example.back.model.Employee;
import com.example.back.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 1. 取得所有員工名單
	@GetMapping("/list")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	// 2. 新增或編輯員工
	@PostMapping("/save")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
		if (employee.getId() == null) {
            // 如果是新員工，給予預設密碼 123456 (並加密)
            employee.setPassword(passwordEncoder.encode("1234"));
            employee.setDepartment("Sales"); // 預設部門
        } else {
            // 如果是編輯，保留原本的密碼
            Employee existing = employeeRepository.findById(employee.getId()).orElse(null);
            if (existing != null) {
                employee.setPassword(existing.getPassword());
            }
        }
        
        employeeRepository.save(employee);
        return ResponseEntity.ok("員工儲存成功！");
	}
	// 3. 刪除員工
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer id){
		employeeRepository.deleteById(id);
        return ResponseEntity.ok("員工刪除成功！");
	}

}
