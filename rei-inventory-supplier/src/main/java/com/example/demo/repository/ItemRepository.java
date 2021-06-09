package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.module.*;
import java.util.*;
@Repository
public interface ItemRepository extends MongoRepository<Item, String>{

	@Query("{productId : {$eq: ?0}}")
	public List<Item> finditemsByProductId(String productId);
}
