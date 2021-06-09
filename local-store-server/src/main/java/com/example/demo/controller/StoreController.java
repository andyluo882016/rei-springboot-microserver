package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.services.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.exception.EmptyException;
import com.example.demo.module.*;
@RestController
@RequestMapping("/store")
public class StoreController {
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    @Autowired
	LocalStoreService LocalStoreService;
    @GetMapping(path="/findalls", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Store>> findAlls( @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
 	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "search-all-stores");
	    
	    logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	    logger.info("Header REI-COM-LOCATION :: " + headerLocation);
		
    	List<Store> list=LocalStoreService.findAllstores();
    	
    	 Optional<List<Store>> oplist= Optional.of(list);
		 if (oplist.isEmpty() || list.isEmpty()) {
			 throw new EmptyException("Empty List");
		 }
		 return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //findNeraByStores
    @GetMapping(path="/findbylocation/{location}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Store>> findNearStoreByLocation(@PathVariable("location") final String location,
    		@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
  	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		) {
    	 logger.info("Header REI-COM-PERSIST :: " + headerPersist);
	     logger.info("Header REI-COM-LOCATION :: " + headerLocation);
		HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "search-nearby local stores");
    	  List<Store> list= LocalStoreService.findNeraByStores(location);
    	  //list.forEach(System.out::println);
    	// List<Store> loclist= list.stream().filter(sl ->(sl.getLocation().getName()).equalsIgnoreCase(location)).collect(Collectors.toList());
    	 
    	 return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
    	  
    }
    @GetMapping(path="/findbyname/{name}/{location}")
    public ResponseEntity<List<Store>> findByNameAndLocation(@PathVariable("name") final String name,
    		@PathVariable("location") final String location,
    		@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
  	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		){
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("HeaderREI-COM-LOCATION :: " + headerLocation);
		HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "search-nearby stores by name");
    	//Location location = new Location("CHARLOTTE", "NC");
    	List<Store> list= LocalStoreService.findSotreByNameAndLocation(name, location);
    	
    	//List<Store> nlist=list.stream().filter(lc ->lc.getLocation().equals(location) && lc.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    	 return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
    }
    @PostMapping(path="/addStore", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> saveone(@Valid @RequestBody Store store,
    		@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
  	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		) {
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("HeaderREI-COM-LOCATION :: " + headerLocation);
		HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "add one store");
    	     LocalStoreService.addStore(store);
    	     return new ResponseEntity<Store>(store, HttpStatus.CREATED);
    }
    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable("id") final String id,
    		@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
  	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		) {
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("HeaderREI-COM-LOCATION :: " + headerLocation);
		HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "delete one store");
    	LocalStoreService.deleteOneById(id);
    	
    	//return "The Store with : "+id +" has beed deleted";
    	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> updateOneStore(@RequestBody Store store,
    		 @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
  	       @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		) {
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("HeaderREI-COM-LOCATION :: " + headerLocation);
		
		HttpHeaders header = new HttpHeaders();
	    header.add("eri-store", "updated one store");
    	LocalStoreService.updatestore(store);
    	//  return ResponseEntity.status(HttpStatus.OK).headers(header).body(item);
    	return ResponseEntity.status(HttpStatus.OK).headers(header).body(store);
    			//"The Store with : "+ store.getId() +" has beed updated";
    }
}
