package com.niuma.rainapiorder;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.niuma")
@EnableDubbo
@MapperScan("com.niuma.rainapiorder.mapper")
public class BinapiOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BinapiOrderApplication.class, args);
    }

}
