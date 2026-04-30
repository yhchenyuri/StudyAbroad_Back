package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.back.model.Employee;
import com.example.back.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 從資料庫尋找該員工編號
		Employee employee = employeeRepository.findByEmployeeCode(username).orElseThrow(() -> new UsernameNotFoundException("找不到此員工帳號: " + username));
		
		// 檢查員工是否在職 (is_active)
		if (employee.getIsActive() != null && !employee.getIsActive()) {
            throw new RuntimeException("此帳號已被停權");
        }
		
		// 回傳 Spring Security 認可的 User 物件
		return User.builder()
                .username(employee.getEmployeeCode())
                .password(employee.getPassword())
                .roles(employee.getRole().toUpperCase()) // admin 或 staff
                .build();
	}

}
