package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.module.*;



//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.*;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class InventoryManager {

	@Autowired
	RestTemplate restTemplate;
	   
	  @HystrixCommand(fallbackMethod="getFallbackItem",
   			commandProperties = { 
   					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
   					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
   					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
   					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
   	   })
       public ResponseEntity<Item[]> getAlls() {
		
        String url="http://producet-inventory-server/inventory/getalls";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA-WA");
	  
		  HttpEntity<Item[]> entity = new HttpEntity<Item[]>(headers);
		  
		 ResponseEntity<Item[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Item[].class);
		 
		 return response;
		  
	}
       //Item(String id, String productId, int quantity, Location location)
    public  ResponseEntity<Item[]> getFallbackItem() {
  		 Item item = new Item(null, null, 0, null);
  		 Item[] items = {item};
  		 
  		return new ResponseEntity<>(items, HttpStatus.EXPECTATION_FAILED);
  	}
    
   @HystrixCommand(fallbackMethod="getFallbackStore",
   			commandProperties = { 
   					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
   					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
   					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
   					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
   	})
    public  ResponseEntity<Store[]> getStoresNearBy(String location) {
    	
    	final String url="http://local-store/store/findbylocation/"+location;
        
    	HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA-WA");
	    headers.add("desc", "search nearby local stores");
	  
		  HttpEntity<Store[]> entity = new HttpEntity<Store[]>(headers);
		  
		 ResponseEntity<Store[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Store[].class);
		 
		 return response;
         
    }
    
    public  ResponseEntity<Store[]> getFallbackStore(String location) {
		 Store store = new Store("not store to be found", null, null);
		 Store[] stores = {store};
		 
		return new ResponseEntity<>(stores, HttpStatus.EXPECTATION_FAILED);
	}
    public ResponseEntity<List<Store>> findAllstores() {
    	
    	final String url="http://local-store/store/findalls";
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
	    //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA");
	    headers.add("desc", "find all stores");
	    HttpEntity<List<Store>> entity = new HttpEntity<List<Store>>(headers);
	    ResponseEntity<List<Store>> response = restTemplate
	    	       .exchange(url, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Store>>() {
	    	 });
	    
	    return response;
    }
    
    public ResponseEntity<Void> updateStore(Store store) {
    	
    	final String url="http://local-store/store/update";
    	HttpHeaders header = new HttpHeaders();
    	header.setContentType(MediaType.APPLICATION_JSON);
	  //  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    header.set("REI-COM-PERSIST", "true");
	    header.set("REI-COM-LOCATION", "USA");
	    header.add("desc", "find all stores");
	    
	    HttpEntity<Store> httpEntity = new HttpEntity<Store>(store, header);
	    
	    
		ResponseEntity<Store> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Store.class);
		System.out.println("Status Code: " + responseEntity.getStatusCode());
		System.out.println(responseEntity.getBody());
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
    }
    
    public ResponseEntity<Item> saveoneItem(@Valid @RequestBody Item item) {
    	  String url="http://producet-inventory-server/inventory/save";
    	  
    	  HttpHeaders header = new HttpHeaders();
    	  header.setContentType(MediaType.APPLICATION_JSON);
  	      //header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
  	   
  	      header.set("REI-COM-PERSIST", "true");
  	      header.set("REI-COM-LOCATION", "USA-WA");
  	  
  		 // HttpEntity<Item[]> entity = new HttpEntity<Item[]>(headers);
  		  
  		HttpEntity<Item> httpEntity = new HttpEntity<Item>(item, header);

		
		ResponseEntity<Item> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Item.class);
		
		System.out.println("Status Code: " + responseEntity.getStatusCode());
		System.out.println("Id: " + responseEntity.getBody().getId());
		System.out.println("Location: " + responseEntity.getHeaders().getLocation());
		
		return responseEntity;

    }
    
    public ResponseEntity<Void> deleteItemById(@Valid @PathVariable("id") final String id) {
    	String url="http://producet-inventory-server/inventory/delete/{id}";
  	  
  	  HttpHeaders header = new HttpHeaders();
  	  header.setContentType(MediaType.APPLICATION_JSON);
	      //header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	      header.set("REI-COM-PERSIST", "true");
	      header.set("REI-COM-LOCATION", "USA-WA");
	  
		
		     HttpEntity<?> httpEntity = new HttpEntity<>(header);
		     
		     ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Void.class, id);
		     
		     System.out.println("Status Code: " + responseEntity.getStatusCode());
		     
		     return responseEntity;
    }
}
