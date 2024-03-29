package com.bdtc.technews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechNewsApplication.class, args);
	}
}
