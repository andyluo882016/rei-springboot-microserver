package com.example.demo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.kafkaserver.KafkaProductProducer;
import com.example.demo.module.Cart;
import com.example.demo.module.CheckOutProducts;
import com.example.demo.module.Item;
import com.example.demo.module.Product;


@Service
public class PurchaseService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	KafkaProductProducer kafkaProductProducer;
	
	Map<String, CheckOutProducts> cmap = new HashMap<String, CheckOutProducts>();
	
	public ResponseEntity<List<Item>> addProduct(String productId, String lname) {
		
		String _url1="http://producet-inventory-server/inventory/getitems/"+productId;
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA");
	    headers.add("cart", "purchase-products");
	    HttpEntity<Item[]> entity = new HttpEntity<Item[]>(headers);
	    	    
	    ResponseEntity<Item[]> itemlists = restTemplate.exchange(_url1, HttpMethod.GET, entity, Item[].class);
		
		 List<Item> ilist=Arrays.asList(itemlists.getBody()).stream()
		 .filter(tms -> (tms.getLocation().getName()).equalsIgnoreCase(lname))
		 .collect(Collectors.toList());
		
		
	   return new ResponseEntity<List<Item>>(ilist, HttpStatus.OK);
		 
	}

	/*@HystrixCommand(fallbackMethod="getFallbackProduct",
			commandProperties = { 
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
					//@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	})*/
	public ResponseEntity<Cart> PurchaseProduct(String locationName, Integer total, Product product) {
		Cart cart =new Cart();
		 Item myitem =new Item();
	
		 String productId=product.getProductId();
		String _url1="http://producet-inventory-server/inventory/getitems/"+productId;
	
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA");
	    headers.add("cart", "purchase-products");
	    
        HttpEntity<Item[]> entity = new HttpEntity<Item[]>(headers);
	    	
	    
	    ResponseEntity<Item[]> itemlists = restTemplate.exchange(_url1, HttpMethod.GET, entity, Item[].class);
		
		 List<Item> ilist=Arrays.asList(itemlists.getBody()).stream()
		 .filter(tms -> (tms.getLocation().getName()).equalsIgnoreCase(locationName))
		 .collect(Collectors.toList());
		 System.out.println("\n--->>>>> list ");
		 ilist.forEach(System.out::println);
		 myitem=ilist.get(0);
		
		 
		 int change =myitem.getQuantity() - total;
		 
		  if(change > 0) {
			  myitem.setQuantity(change);
		  }else {
			  myitem.setQuantity(0);
		  }
	
	  String myid = UUID.randomUUID().toString();
	
	   double mypay=Math.multiplyExact(total, product.getPrice());
	   CheckOutProducts ckproduct= new CheckOutProducts(product, total, mypay);
	    cart.setId(myid);
	    cart.setItem(myitem);
	    cart.setProduct(ckproduct);
	    cart.setTotal(total);
	    double mycharge =0.0;
	    cart.setTotal(total);
	    cmap.put(myid, ckproduct);
	    for(Map.Entry<String, CheckOutProducts> mps : cmap.entrySet()) {
	    	mycharge = mycharge+mps.getValue().getTopay();
	    	cart.getPlist().add(mps.getValue());
	    }
	    cart.setCharge(mycharge);
	    
	  
	   kafkaProductProducer.sendMessage(myitem);
	   return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		 
	}

	 public  ResponseEntity<Cart> getFallbackProduct(String locationName, Integer total, Product product) {
		
		     Cart cart =new Cart("default id", null, null, 0, 0.0);
		 
		return new ResponseEntity<>(cart, HttpStatus.EXPECTATION_FAILED);
	}
	 @HystrixCommand(fallbackMethod="getFallbackProductss",
				commandProperties = { 
						@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="6000"),
						@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
						@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
						@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="9000")
						//@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
		})
	 public ResponseEntity<Cart> SelectItem(String locationName, Integer total, String productId, Product product) {
		 Cart cart =new Cart();
		 Item myitem =new Item();
	
		String _url1="http://producet-inventory-server/inventory/getitems/"+productId;
	
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   
	    headers.set("REI-COM-PERSIST", "true");
	    headers.set("REI-COM-LOCATION", "USA");
	    headers.add("cart", "purchase-products");
	    
        HttpEntity<Item[]> entity = new HttpEntity<Item[]>(headers);
	    	
	    
	    ResponseEntity<Item[]> itemlists = restTemplate.exchange(_url1, HttpMethod.GET, entity, Item[].class);
		
		 List<Item> ilist=Arrays.asList(itemlists.getBody()).stream()
		 .filter(tms -> (tms.getLocation().getName()).equalsIgnoreCase(locationName))
		 .collect(Collectors.toList());
		 System.out.println("\n--->>>>> list ");
		 ilist.forEach(System.out::println);
		 myitem=ilist.get(0); 
		 int change =myitem.getQuantity() - total;
		 
		  if(change > 0) {
			  myitem.setQuantity(change);
		  }else {
			  myitem.setQuantity(0);
		  }
	
		  String myid = UUID.randomUUID().toString();
		 
		   double mypay=Math.multiplyExact(total, product.getPrice());
		   CheckOutProducts ckproduct= new CheckOutProducts(product, total, mypay);
		    cart.setId(myid);
		    cart.setItem(myitem);
		    cart.setProduct(ckproduct);
		    cart.setTotal(total);
		    double mycharge =0.0;
		    cart.setTotal(total);
		    cmap.put(myid, ckproduct);
		    for(Map.Entry<String, CheckOutProducts> mps : cmap.entrySet()) {
		    	mycharge = mycharge+mps.getValue().getTopay();
		    	cart.getPlist().add(mps.getValue());
		    }
		    cart.setCharge(mycharge);
		   
		   kafkaProductProducer.sendMessage(myitem);
		   return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		  
	
	}
	 
	 public  ResponseEntity<Cart> getFallbackProductss(String locationName, Integer total, String productId, Product product) {
			
	     Cart cart =new Cart(productId, null, null, 0, 0.0);
	 
	return new ResponseEntity<>(cart, HttpStatus.EXPECTATION_FAILED);
    }

	public String resettheMap() {
		this.cmap.clear();
		System.out.println("The Map has been reseted, now the size of the map:"+cmap.size());
		return "The Map has been reseted, now the size of the map:"+cmap.size();
	}
}
