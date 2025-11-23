package com.JavaWebmlzz.ad_management.controller;

import com.JavaWebmlzz.ad_management.dto.ApiResponse;
import com.JavaWebmlzz.ad_management.dto.LoginRequest;
import com.JavaWebmlzz.ad_management.entity.AdminUser;
import com.JavaWebmlzz.ad_management.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminUserService adminUserService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        Optional<AdminUser> userOpt = adminUserService.getUserByUsername(loginRequest.getUsername());

        if (userOpt.isPresent() &&
                adminUserService.validatePassword(loginRequest.getPassword(), userOpt.get().getPassword())) {
            // TODO: 生成JWT token或session
            return ResponseEntity.ok(ApiResponse.success("登录成功"));
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