package com.take_out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author YAO
 * @create 2022-08-22 14:33
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class takeOutApplication {
    public static void main(String[] args) {
        SpringApplication.run(takeOutApplication.class,args);
    }
}
