package com.example.demo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.example.demo.module.Location;
import com.example.demo.module.Store;
import com.example.demo.repository.StoreRepository;

@SpringBootApplication
@EnableEurekaClient
public class LocalStoreServerApplication implements CommandLineRunner{
    @Autowired
	StoreRepository storeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LocalStoreServerApplication.class, args);
		System.out.println("The local-store server is run: 8803");
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<Store> list=new LinkedList<Store>();
		 Location lc1= new Location("SEATTLE", "WA");
		 Location lc2= new Location("CHARLOTTE", "NC");
		 Location lc3= new Location("ONLINE", "USA");
		 Location lc4= new Location("TACOMA", "WA");
		 Location lc5= new Location("GREENVILLE", "NC");
		 Location lc6= new Location("ASHEVILLE", "NC");
		 //Store(String id, String name, Location location)
		 Store st1 = new Store("WA123456", "Issaquah", lc1);
		 Store st2 = new Store("WA123478", "Sammamish", lc1);
		 Store st3 = new Store("NC123490", "Concord", lc2);
		 Store st4 = new Store("NC123510", "Pineville", lc2);
		 Store st5 = new Store("ON222888", "USA", lc3);
		 Store st6 = new Store("WA123488", "Waller", lc4);
		 Store st7 = new Store("NC123885", "Medical District", lc5);
		 Store st8 = new Store("NC123789", "Greenville", lc6);
		 Store st9 = new Store("NC123700", "Westwood Place", lc6);
		 
		 Collections.addAll(list,  st1, st2, st3, st4, st5, st6, st7, st8,st9);
		 
		 storeRepository.saveAll(list);
	}

}
