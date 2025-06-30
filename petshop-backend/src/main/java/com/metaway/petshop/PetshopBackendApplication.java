package com.metaway.petshop;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@MapperScan("com.metaway.petshop.mappers")
@RequiredArgsConstructor
@SpringBootApplication
@EnableJms
public class PetshopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopBackendApplication.class, args);
    }

}
