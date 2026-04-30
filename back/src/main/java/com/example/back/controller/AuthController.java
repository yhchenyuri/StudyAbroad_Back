package com.example.back.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@GetMapping("/me")
	public Map<String, String> getCurrentUser(Authentication authentication){
		Map<String, String> userInfo = new HashMap<>();
		if (authentication != null && authentication.isAuthenticated()) {
			userInfo.put("username", authentication.getName());
			
			// 取得使用者的角色 (Spring Security 會自動加上 ROLE_ 前綴，例如 ROLE_ADMIN)
			String role = authentication.getAuthorities().iterator().next().getAuthority();
            userInfo.put("role", role);
		}
		return userInfo;
	}

}
