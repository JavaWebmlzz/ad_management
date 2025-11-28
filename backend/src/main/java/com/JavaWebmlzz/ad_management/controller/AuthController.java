package com.JavaWebmlzz.ad_management.controller;

import com.JavaWebmlzz.ad_management.dto.ApiResponse;
import com.JavaWebmlzz.ad_management.dto.LoginRequest;
import com.JavaWebmlzz.ad_management.entity.AdminUser;
import com.JavaWebmlzz.ad_management.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminUserService adminUserService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest loginRequest) {
        Optional<AdminUser> userOpt = adminUserService.getUserByUsername(loginRequest.getUsername());

        if (userOpt.isPresent() &&
                adminUserService.validatePassword(loginRequest.getPassword(), userOpt.get().getPassword())) {

            // 创建响应数据（实际项目中应该生成JWT token）
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", "demo-token-" + System.currentTimeMillis());
            responseData.put("user", userOpt.get().getUsername());

            return ResponseEntity.ok(ApiResponse.success(responseData));
        }

        return ResponseEntity.badRequest().body(ApiResponse.error("用户名或密码错误"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AdminUser>> register(@RequestBody AdminUser user) {
        try {
            AdminUser newUser = adminUserService.createUser(user);
            return ResponseEntity.ok(ApiResponse.success(newUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("注册失败: " + e.getMessage()));
        }
    }
}