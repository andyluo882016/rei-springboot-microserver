package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ReiEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReiEurekaServerApplication.class, args);
		System.out.println("Eureka-Server is up ");
	}

}
