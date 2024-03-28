package com.bdtc.technews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients
public class TechNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechNewsApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("http:127.0.0.1:3000")
//						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//						.allowedHeaders("*")
//				;
//			}
//		};
//	}
}
