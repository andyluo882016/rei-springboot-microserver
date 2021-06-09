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
import com.example.demo.repository.ItemRepository;

@SpringBootApplication
@EnableEurekaClient
public class ReiInventorySupplierApplication implements CommandLineRunner{
	@Autowired
	ItemRepository itemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ReiInventorySupplierApplication.class, args);
		System.out.println("The Inventory server run at: 8802");
	}

	@Override
	public void run(String... args) throws Exception {
		 List<Item> list=new LinkedList<Item>();
		 Location lc1= new Location("SEATTLE", "WA");
		 Location lc2= new Location("CHARLOTTE", "NC");
		 Location lc3= new Location("ONLINE", "USA");
		 Location lc4= new Location("TACOMA", "WA");
		 Location lc5= new Location("GREENVILLE", "NC");
		 Location lc6= new Location("ASHEVILLE", "NC");
		//Item(String productId, int quantity, Location location)
		 Item item1=new Item("123456", 10, lc1);
		 Item item2=new Item("123456", 100, lc2);
		 Item item3=new Item("222222", 30, lc3);
		 Item item4=new Item("222222", 120, lc4);
		 Item item5=new Item("333333", 56, lc1);
		 Item item6=new Item("908765", 100, lc4);
		 Item item7=new Item("384123", 107, lc3);
		 Item item8=new Item("384123", 148, lc6);
		 Item item9=new Item("999999",65, lc6);
		 Item item10=new Item("383748", 28, lc5);
		 Item item11=new Item("333333", 10, lc3);
		 
		 Collections.addAll(list, item1, item2, item3, item4, item5, item6, item7, item8,item9,item10,item11);
		 
		 itemRepository.saveAll(list);
	}

}
