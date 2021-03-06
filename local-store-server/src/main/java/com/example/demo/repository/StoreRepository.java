package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.example.demo.module.Location;
import com.example.demo.module.Store;

@Repository
public interface StoreRepository extends MongoRepository<Store, String>{

	 @Query(value = "{location : ?0")
	 public List<Store> findStoresBytheLocation(Location location);
	 @Query("{name: {$eq : ?0}}")
	 public List<Store> findStoresByName(String name);
	 @Query("{name: {$eq : ?0}, location : ?1}")
	 public List<Store> findStoreBynameAndLocation(String name, Location location);
}
