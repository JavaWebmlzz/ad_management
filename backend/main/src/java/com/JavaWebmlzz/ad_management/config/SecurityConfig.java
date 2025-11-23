package com.JavaWebmlzz.ad_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 暂时禁用CSRF，开发阶段方便测试
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 认证接口公开
                        .requestMatchers("/api/sdk/**").permitAll()  // SDK接口公开
                        .anyRequest().authenticated() // 其他接口需要认证
                )
                .formLogin(form -> form.disable()) // 禁用默认登录页
                .httpBasic(httpBasic -> httpBasic.disable()); // 禁用HTTP Basic

        return http.build();
    }


}