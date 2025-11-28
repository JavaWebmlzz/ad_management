//// CorsConfig.java
//package com.JavaWebmlzz.ad_management.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//// CorsConfig.java
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders(
//                        "authorization",
//                        "content-type",
//                        "x-requested-with",
//                        "x-ijt",  // 明确允许 x-ijt 头
//                        "*"       // 同时也允许其他头
//                )
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}