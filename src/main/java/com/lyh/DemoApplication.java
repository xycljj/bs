package com.lyh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author haozidada
 */
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "com.lyh.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
