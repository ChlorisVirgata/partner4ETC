package com.allinpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.allinpay.mapper")
@EnableScheduling
public class ETCManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETCManageApplication.class, args);
    }

}
