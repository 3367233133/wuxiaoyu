package com.niuma.rainapi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//{
//        "captcha": "1762",
//        "checkPassword": "qaz123321,,..",
//        "phoneCaptcha": "85362",
//        "phoneNum": "15250660272",
//        "userAccount": "wuxy",
//        "userName": "wuxy",
//        "userPassword": "qaz123321,,.."
//        }


@SpringBootApplication(scanBasePackages = "com.niuma")
@MapperScan("com.niuma.rainapi.mapper")
@EnableDubbo
public class RainapiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainapiBackendApplication.class, args);

    }

}
