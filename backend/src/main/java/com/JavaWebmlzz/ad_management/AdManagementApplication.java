package com.JavaWebmlzz.ad_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AdManagementApplication {

    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(AdManagementApplication.class, args);
        Environment env = context.getEnvironment();

        // 打印数据库配置，验证是否加载
        System.out.println("===== 配置加载验证 =====");
        System.out.println("应用名称: " + env.getProperty("spring.application.name"));
        System.out.println("数据库URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("数据库用户名: " + env.getProperty("spring.datasource.username"));
        System.out.println("服务端口: " + env.getProperty("server.port"));
        System.out.println("=======================");
    }

}
