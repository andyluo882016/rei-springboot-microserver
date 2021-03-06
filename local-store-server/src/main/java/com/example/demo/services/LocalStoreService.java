package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.module.*;
import com.example.demo.repository.StoreRepository;

@Service
public class LocalStoreService {
    @Autowired
	StoreRepository storeRepository;
    
    public List<Store> findAllstores() {
    	
    	return storeRepository.findAll();
    }
    
    public List<Store> findNeraByStores(String location) {
    	 List<Store> slist=storeRepository.findAll();
    	  slist.forEach(System.out::println);
    	  System.out.println("\n-----------------");
    	 List<Store> list=slist.stream().filter(st ->(st.getLocation().getName()).equalsIgnoreCase(location)).collect(Collectors.toList());
          list.forEach(System.out::println);
    	 return list;
    }
    
    public List<Store> findSotreByNameAndLocation(String name, String location) {
    	
    	      List<Store> slist=storeRepository.findAll();
  	          slist.forEach(System.out::println);
  	          System.out.println("\n--------****---------");
  	        List<Store> list=slist.stream().filter(st ->(st.getLocation().getName()).equalsIgnoreCase(location))
  	        		.filter(sl ->(sl.getName()).equalsIgnoreCase(name))
  	        		.collect(Collectors.toList());
    	   return list;
    }
    
    public Store addStore(Store store) {
    	return storeRepository.insert(store);
    }
    
    public void updatestore(Store store) {
          storeRepository.save(store);
    }
    
    public void deleteOneById(String id) {
    
    	storeRepository.deleteById(id);
    }
}
