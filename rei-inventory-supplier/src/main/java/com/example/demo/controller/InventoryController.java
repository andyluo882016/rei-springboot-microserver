package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.validation.Valid;

import com.example.demo.exception.EmptyCollectionException;
import com.example.demo.module.*;
import com.example.demo.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
   @Autowired
	InventoryService inventoryService;
   
   @GetMapping(path="/findall")
   public ResponseEntity<List<Item>> findalls( ) {
	    HttpHeaders header = new HttpHeaders();
	    header.add("desc", "List of Products that are in the Inventory");
	    header.add("type", "product object");
	    List<Item> list=inventoryService.findalls();
	    
	    return ResponseEntity.status(HttpStatus.OK).headers(header).body(list);
   }
 
   @GetMapping(path="/findone/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Item> findById(@PathVariable("id") final String id,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		   ) {
	     HttpHeaders header = new HttpHeaders();
	     header.add("desc", "find one Item by item id");
	     logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	     logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	     Item item= inventoryService.findById(id);
	     
	     return new ResponseEntity<Item>(item, header, HttpStatus.OK);
   }
  
   @PostMapping(path="/save", consumes =MediaType.APPLICATION_JSON_VALUE , produces =MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Item> saveoneitem(@Valid @RequestBody Item item,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		 
		   ) {
	     HttpHeaders header = new HttpHeaders();
	     header.add("desc", "Adding one Item");
	     logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	     logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	     Item tm =inventoryService.saveOne(item);
	     Optional<Item> optim=Optional.of(tm);
	     
	     return optim.isPresent() ? new ResponseEntity<Item>(tm, HttpStatus.CREATED) : new ResponseEntity<Item>(HttpStatus.BAD_REQUEST);
	     
   }
   
   @PutMapping(path="/update", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Item> updatedOne(@RequestBody Item item,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		   ) {
	   HttpHeaders header = new HttpHeaders();
	     header.add("desc", "updated one item");
	   logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	   logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	   //Optional<Item> optim=Optional.of(inventoryService.updatedItemByid(item));
	    return ResponseEntity.status(HttpStatus.OK).headers(header).body(item);
	   //return new ResponseEntity<Item>(item, HttpStatus.OK);
			   //optim.isPresent() ? ResponseEntity.status(HttpStatus.ACCEPTED).body(optim.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(item);
   }
   @DeleteMapping(path="/delete/{id}")
   public ResponseEntity<Void> deletItemById(@PathVariable("id") final String id,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		   ) {
	   logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	   logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	    inventoryService.deleteById(id);
	    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	  
   }


 @GetMapping(path="/getitems/{pid}", produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<Item>> findByProductId(@Valid @PathVariable("pid") final String pid,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		   ) {
	   logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	   logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	   List<Item> list= inventoryService.findbyProductId(pid);
	   HttpHeaders header = new HttpHeaders();
	   header.add("desc", "Nothing be finded");
	   Optional<List<Item>> optim=Optional.of(list);
	   if(!list.isEmpty()) {
		   return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
	   }else if(list.isEmpty()) {
		   throw new EmptyCollectionException(pid);
		  // return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(null);
	   }else {
		   throw new EmptyCollectionException(pid);
		   //return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(header).body(null);   
	   }
	   //return new ResponseEntity<List<Item>>(optim.get(), HttpStatus.OK);
	  //return optim.isPresent() ? new ResponseEntity<List<Item>>(optim.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	   
   }

 @GetMapping(path="/getalls", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Item>> finalls( @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation){
		logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("Header REI-COM-LOCATION :: " + headerLocation);
		//inventoryService.findalls();
		 List<Item> blist=inventoryService.findalls();
		 
		 blist.stream().forEach(p ->System.out.println(p.toString()));
		 
		 Optional<List<Item>> oplist= Optional.of(blist);
		 if (oplist.isEmpty()) {
			 throw new EmptyCollectionException("Empty List");
		 }
		 return new ResponseEntity<List<Item>>(blist, HttpStatus.OK);
	}
  // @PutMapping(path="/update/{tid}/{num}")
 @RequestMapping(path="/update/{tid}/{num}")
   public ResponseEntity<Void> updatedQunatityByid(@PathVariable("tid") String tid, @PathVariable("num") Integer num,
		   @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
		   ) {
	   
	   logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("Header REI-COM-LOCATION :: " + headerLocation);
		 HttpHeaders header = new HttpHeaders();
		 header.add("desc", "updated one item");
		inventoryService.updateItemQuantity(tid, num);
		
		return new ResponseEntity<Void>(header,HttpStatus.OK);
	   
   }
  
}
