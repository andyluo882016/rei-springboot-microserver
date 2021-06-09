package com.example.demo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.example.demo.module.*;
import com.example.demo.repository.ProductRepository;

@SpringBootApplication
@EnableEurekaClient
public class ReiProductSupplierApplication implements CommandLineRunner{

	@Autowired
	ProductRepository productRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ReiProductSupplierApplication.class, args);
		System.out.println("The product supplier run at: 8801");
	}

	@Override
	public void run(String... args) throws Exception {
	
		 List<Product> list=new LinkedList<Product>();
		 
		 Product pd1=new Product("123456", "REI", "Tent", 10);
		 Product pd2=new Product("222222", "Patagonia", "Micro", 12);
		 Product pd3=new Product("333333", "Black Diamond", "Hiking Poles", 18);
		 Product pd4=new Product("098762", "The North Face", "Rain Jacket", 68);
		 Product pd5=new Product("999999", "Big Agnes", "Backpacking Tent", 129);
		 Product pd6=new Product("977633", "Blundestone", "Boots", 234);
		 Product pd7=new Product("338290", "The Landmark Project", "Shirt", 45);
		 Product pd8=new Product("226688", "National Geographic", "Map", 13);
		 Product pd9=new Product("119966", "Yeti", "Mug", 50);
		 Product pd10=new Product("887432", "Filson", "Hat", 30);
		 Product pd11=new Product("384123","REI","Rain Pant",35);
		 Product pd12 = new Product("125385","Patagonia","Shorts",50);
		 
		 Collections.addAll(list, pd1, pd2, pd3, pd4, pd5, pd6, pd7, pd8, pd9, pd10, pd11, pd12);
		 
		// productRepository.deleteAll();
		 
		 productRepository.saveAll(list);
		
	}

}
