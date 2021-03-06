package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.validation.Valid;

import com.example.demo.RibbonConfiguration;
import com.example.demo.module.*;
import com.example.demo.service.InventoryManager;

@RestController
@RequestMapping("/inventoryManager")
@CrossOrigin("*")
@RibbonClient(name = "product-consumer-center", configuration = RibbonConfiguration.class)
public class InventoryManagerController {
    @Autowired
	InventoryManager inventoryManager;
	
	 @GetMapping(path="/getalls", produces =MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Item[]> getalls() {
	    	System.out.println("\n\n**** "+inventoryManager.getAlls());
	    	return inventoryManager.getAlls();
	    }
	  
	
	  @GetMapping(path="/neraby/{location}", produces=MediaType.APPLICATION_JSON_VALUE)  
	  public ResponseEntity<Store[]> findNeraBystoresByLocation(@PathVariable("location") final String location) {
		  
		  return inventoryManager.getStoresNearBy(location);
	  }
	  
	  @GetMapping(path="/findallstores", produces=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<List<Store>> findallstores() {
		  return inventoryManager.findAllstores();
	  }
	  @PutMapping(path="/updateStore", consumes= MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void> updateStore(@Valid @RequestBody Store store) {
		
		      return inventoryManager.updateStore(store);
	  }
	  @PostMapping(path="/saveItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Item> saveOneItem(@Valid @RequestBody Item item) {
		  
		  return inventoryManager.saveoneItem(item);
	  }
	  @DeleteMapping(path="/deleteItem/{id}")
	  public ResponseEntity<Void> deletItemById(@Valid @PathVariable("id") final String id) {
		  
		     return inventoryManager.deleteItemById(id);
	  }
	    
}
