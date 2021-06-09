package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.module.Product;
import java.util.*;
@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	  @Query(value = "{productId : ?0}")
	  public Product findProductByProductId(String productId);
      
	  @Query(value ="{brand : {$eq : ?0}}")
	  public List<Product> findProductsByBrand(String brand);
	  @Query(value ="{price : {$eq : ?0}")
	  public List<Product> findProductByPrice(int price);
}
