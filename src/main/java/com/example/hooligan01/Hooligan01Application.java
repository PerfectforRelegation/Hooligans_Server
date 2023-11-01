package com.example.hooligan01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Hooligan01Application {

	public static void main(String[] args) {
		SpringApplication.run(Hooligan01Application.class, args);
	}

}
