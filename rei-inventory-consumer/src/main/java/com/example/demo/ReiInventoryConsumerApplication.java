package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
//import org.springframework.web.reactive.function.client.WebClient;

//import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ReiInventoryConsumerApplication {
	Logger log = LoggerFactory.getLogger(ReiInventoryConsumerApplication.class);
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		 
	    return builder
	            .setConnectTimeout(Duration.ofMillis(3000))
	            .setReadTimeout(Duration.ofMillis(3000))
	            .build();
	}
	
	
	/*@Bean
	public WebClient getWebClisnt() {
	
		  WebClient webClient =WebClient.builder()
		                         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).filter(logRequest())
    	                     .filter(logResponse()).build();
		  return webClient;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(ReiInventoryConsumerApplication.class, args);
	}
	
	/* private ExchangeFilterFunction logRequest() {
			
		   return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->{
			   log.info("Request {} {} ", clientRequest.method(), clientRequest.url());
			   clientRequest.headers()
			   .forEach((name, values) -> values.forEach(value ->log.info("{}={}", name, value)));
		   return Stream.of(clientRequest);
				   //Mono.just(clientRequest);
		   });
	   }
	   
	   private ExchangeFilterFunction logResponse() {
			return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
				log.info("Response status code {}", clientResponse.statusCode());
				return Mono.just(clientResponse);
			});
		}*/

}
