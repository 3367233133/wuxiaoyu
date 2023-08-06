package com.niuma.rainapithirdparty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author niumazlb
 */
@SpringBootApplication(scanBasePackages = "com.niuma")
@MapperScan("com.niuma.rainapithirdparty.mapper")
public class RainapiThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainapiThirdPartyApplication.class, args);
    }

}
