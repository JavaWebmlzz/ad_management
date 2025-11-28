package com.JavaWebmlzz.ad_management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ad_rule")
@Data
public class AdRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad_id")
    private Long adId;

    @Column(name = "target_categories", length = 500)
    private String targetCategories; // 目标分类，逗号分隔：新闻,体育,财经

    @Column(name = "target_devices", length = 200)
    private String targetDevices; // 目标设备，逗号分隔：PC,MOBILE,TABLET

    @Column(name = "max_impressions_per_user")
    private Integer maxImpressionsPerUser; // 用户最大展示次数/天

    @Column(name = "time_slots", length = 500)
    private String timeSlots; // 投放时间段，JSON格式

    @Column(name = "target_locations", length = 500)
    private String targetLocations; // 目标地域

    @Column(name = "browser_whitelist", length = 500)
    private String browserWhitelist; // 浏览器白名单

    // 关联广告
    @OneToOne
    @JoinColumn(name = "ad_id", insertable = false, updatable = false)
    private Advertisement advertisement;
}