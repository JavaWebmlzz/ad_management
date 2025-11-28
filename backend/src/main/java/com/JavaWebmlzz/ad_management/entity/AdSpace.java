package com.JavaWebmlzz.ad_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ad_space")
@Data
public class AdSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String code; // 广告位代码，用于SDK识别

    @Column(length = 20)
    private String width; // 广告位宽度

    @Column(length = 20)
    private String height; // 广告位高度

    @Column(length = 500)
    private String description;

    private Integer status = 1; // 1-启用, 0-禁用

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}