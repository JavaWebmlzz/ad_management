package com.JavaWebmlzz.ad_management.service;

import com.JavaWebmlzz.ad_management.entity.AdminUser;
import java.util.List;
import java.util.Optional;

public interface AdminUserService {

    AdminUser createUser(AdminUser user);

    Optional<AdminUser> getUserById(Long id);

    Optional<AdminUser> getUserByUsername(String username);

    List<AdminUser> getAllUsers();

    AdminUser updateUser(AdminUser user);

    void deleteUser(Long id);

    boolean validatePassword(String rawPassword, String encodedPassword);
}