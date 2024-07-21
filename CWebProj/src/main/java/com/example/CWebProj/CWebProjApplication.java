package com.example.CWebProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CWebProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(CWebProjApplication.class, args);
	}

}
