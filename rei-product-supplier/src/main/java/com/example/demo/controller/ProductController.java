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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exception.EmptyCollectionException;
import com.example.demo.exception.NoSuchElementFoundException;
import com.example.demo.module.*;
import com.example.demo.servies.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
    @Autowired
	ProductService productService;
    
    @GetMapping(path="/findall", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findalls(@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		logger.info("Header REI-COM-LOCATION :: " + headerLocation);
		List<Product> list=productService.findalls();
		if(list.isEmpty()) {
			throw new EmptyCollectionException("The List is Empty");
		}
		else {
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		}
    }
    @PostMapping(path="/saveone", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	      logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		     logger.info("Header REI-COM-LOCATION :: " + headerLocation);
    	      HttpHeaders header = new HttpHeaders();
	          header.add("eri-product", "add one product");
    	      productService.saveoneProduct(product);
    	     return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    
    	    
    }
    
    @GetMapping(path="/page", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getallpageInfor(@RequestParam(name="Pagenum", defaultValue="0") int Pagenum,
    		@RequestParam(name="pagesize", defaultValue="2") int pagesize,
    		@RequestParam(name="sortBy", defaultValue="id") String sortBy) {
    	
    	  return productService.getallProductsInPage(Pagenum, pagesize, sortBy);
    	
    }
    @GetMapping(path="/find/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> findoneByid(@PathVariable("id") final String id, @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	  logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		  logger.info("Header REI-COM-LOCATION :: " + headerLocation);
 	      HttpHeaders header = new HttpHeaders();
	      header.add("eri-product", "find by id");
           
        Product product = productService.findProductById(id);
        System.out.println("--> "+product.getBrand());
    	    //return ResponseEntity.status(HttpStatus.OK).headers(header).body(product);
        	  return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") final String id, @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	 logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		  logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	      HttpHeaders header = new HttpHeaders();
	      header.add("eri-product", "deleted by id");
    	
    	 if(productService.existsofProductbyId(id)) {
    		 productService.deleteById(id);
    		 String msg ="completly deleted product with id "+id;
    		 return new ResponseEntity<String>(msg, HttpStatus.OK);
    	 }else {
 			logger.info("The Product with the id: {} ",id);
 			logger.info("is not found");
 			throw new NoSuchElementFoundException(id);
 		}
    }
    
    @PutMapping(path="/updated", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updatedProduct(@RequestBody Product product, 
    		  @RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation) {
    	 logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		  logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	      HttpHeaders header = new HttpHeaders();
	      header.add("eri-product", "update product");
    	    Product prod = productService.updatedProduct(product);
    	    
    	    Optional<Product> opt =Optional.of(prod);
    	    if(!opt.isPresent() || opt.isEmpty()) {
    	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prod);
    	    }else {
    	    	 return ResponseEntity.status(HttpStatus.ACCEPTED).body(opt.get());
    	    }
    }
    @GetMapping(path="/findbybrand/{brand}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findbybrand(@PathVariable("brand") final String brand,
    		@RequestHeader(name = "REI-COM-PERSIST", required = true) String headerPersist,
			  @RequestHeader(name = "REI-COM-LOCATION", defaultValue = "USA") String headerLocation
    		) {
    	
    	logger.info("Header REI-COM-PERSIST :: " + headerPersist);
		  logger.info("Header REI-COM-LOCATION :: " + headerLocation);
	      HttpHeaders header = new HttpHeaders();
	      header.add("eri-product", "find by brand");
  	      List<Product> prods = productService.findproductsByBrand(brand);
  	      if (!prods.isEmpty())
  	      return new ResponseEntity<List<Product>>(prods, HttpStatus.OK);
  	      else {
  	    	  throw new NoSuchElementFoundException(brand);
  	      }
    	
    }
}
