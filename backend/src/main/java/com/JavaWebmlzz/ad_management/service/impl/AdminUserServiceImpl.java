package com.JavaWebmlzz.ad_management.service.impl;

import com.JavaWebmlzz.ad_management.entity.AdminUser;
import com.JavaWebmlzz.ad_management.repository.AdminUserRepository;
import com.JavaWebmlzz.ad_management.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminUser createUser(AdminUser user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return adminUserRepository.save(user);
    }

    @Override
    public Optional<AdminUser> getUserById(Long id) {
        return adminUserRepository.findById(id);
    }

    @Override
    public Optional<AdminUser> getUserByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    @Override
    public List<AdminUser> getAllUsers() {
        return adminUserRepository.findAll();
    }

    @Override
    public AdminUser updateUser(AdminUser user) {
        return adminUserRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        adminUserRepository.deleteById(id);
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}