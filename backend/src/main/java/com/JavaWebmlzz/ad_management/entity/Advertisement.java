package com.JavaWebmlzz.ad_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "advertisement")
@Data
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // 广告内容：图片URL、HTML代码等

    @Column(length = 20)
    private String type; // 广告类型：IMAGE, TEXT, VIDEO, HTML

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "link_url", length = 500)
    private String linkUrl; // 点击跳转链接

    @Column(name = "start_time")
    private LocalDateTime startTime; // 投放开始时间

    @Column(name = "end_time")
    private LocalDateTime endTime; // 投放结束时间

    private Integer status = 0; // 0-待审核, 1-已上线, 2-已下线, 3-已过期

    @Column(name = "ad_space_id")
    private Long adSpaceId; // 广告位ID

    private Integer priority = 1; // 优先级，数字越大优先级越高

    @Column(name = "max_impressions")
    private Integer maxImpressions; // 最大展示次数

    @Column(name = "current_impressions")
    private Integer currentImpressions = 0; // 当前展示次数

    @Column(name = "click_count")
    private Integer clickCount = 0; // 点击次数

    @Column(name = "created_by")
    private Long createdBy; // 创建人ID

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
        if (status == null) status = 0;
        if (currentImpressions == null) currentImpressions = 0;
        if (clickCount == null) clickCount = 0;
        if (priority == null) priority = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}