package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
        // 因為我們是靜態 HTML 呼叫 API，先關閉 CSRF 防護避免 403 錯誤
        .csrf(csrf -> csrf.disable()) 
        
        // 設定網址權限
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login.html", "/css/**", "/js/**", "/images/**").permitAll() // 登入頁與靜態資源不需阻擋
            
         // 🌟 限制只有 ADMIN 角色才能呼叫上傳與刪除的 API
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/courses/upload").hasAnyRole("ADMIN","MANAGER")
            .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/courses/*").hasAnyRole("ADMIN","MANAGER")
         // 🌟 【員工管理防護】：嚴格限制只有 ADMIN 才能進入員工頁面與呼叫員工 API！
            .requestMatchers("/staff.html").hasRole("ADMIN")
            .requestMatchers("/api/employees/**").hasRole("ADMIN")
         // 其他請求 (包含讀取課程清單) 一般登入的 staff 都可以看
            .anyRequest().authenticated() // 其他所有網頁 (如 courses.html) 與 API 都需要登入
        )
        
        // 設定登入行為
        .formLogin(form -> form
            .loginPage("/login.html")                  // 指定我們自己寫的登入畫面
            .loginProcessingUrl("/login")              // 告訴 Spring 去攔截表單的 action="/login"
            .defaultSuccessUrl("/courses.html", true)  // 🌟 登入成功後跳轉到課程管理頁面
            .failureUrl("/login.html?error=true")      // 登入失敗跳回登入頁
            .permitAll()
        )
        
        // 設定登出行為
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login.html?logout=true")
            .permitAll()
        );

    return http.build();
}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
