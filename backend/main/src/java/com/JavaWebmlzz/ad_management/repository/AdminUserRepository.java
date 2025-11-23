package com.JavaWebmlzz.ad_management.repository;

import com.JavaWebmlzz.ad_management.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<AdminUser> findByEmail(String email);
}