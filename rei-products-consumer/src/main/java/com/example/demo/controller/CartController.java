package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RibbonConfiguration;
import com.example.demo.module.*;
import com.example.demo.service.PurchaseService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
@RibbonClient(name ="product-consumer-center", configuration = RibbonConfiguration.class)
public class CartController {
    @Autowired
	PurchaseService purchaseService;
    @GetMapping(path="/purchase/{productId}/{itemId}/{lname}/{total}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> showcarts(
    		@PathVariable("productId") final String productId,
    		@PathVariable("itemId") final String itemId,
    		@PathVariable("lname") final String lname,
    		@PathVariable("total") final Integer total
    		) {
    	
    	ResponseEntity<List<Item>> rpcart = purchaseService.addProduct(productId, lname);
    	return rpcart;
    }
 
    @PostMapping(path="/addcart/{locationName}/{total}", consumes =MediaType.APPLICATION_JSON_VALUE , produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> purchaseProducts(
    		@PathVariable("locationName") final String locationName,
    		@PathVariable("total") final Integer total,
    		@RequestBody final Product product
    		
    		) {
    	
    	ResponseEntity<Cart> respose = purchaseService.PurchaseProduct(locationName, total, product);
    	
    	return respose;
    	
    }
     @PutMapping(path="/selectItem/{locationName}/{total}/{productId}", consumes =MediaType.APPLICATION_JSON_VALUE , produces =MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Cart> selectProducts(
    		@PathVariable("locationName") final String locationName,
    		@PathVariable("total") final Integer total,
    		@PathVariable("productId") final String productId,
    		@RequestBody final Product product
    		
    		) {
    	ResponseEntity<Cart> respose = purchaseService.SelectItem(locationName, total, productId, product);
    	
    	return respose;
    	
    }
     @GetMapping(path="/clearmap", produces =MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<String> resetReposiotry() {
    	 
    	    String msg = purchaseService.resettheMap();
    	   
    	    return new ResponseEntity<String>(msg, HttpStatus.OK);
     }
}
