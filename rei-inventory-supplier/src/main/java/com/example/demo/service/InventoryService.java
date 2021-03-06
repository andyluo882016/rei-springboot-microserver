package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.repository.ItemRepository;
import java.util.*;
import com.example.demo.module.*;
@Service
@Transactional
//@CrossOrigin("*")
public class InventoryService {
    @Autowired
	ItemRepository itemRepository;
    
    
    public List<Item> findalls() {
    
    	return itemRepository.findAll();
    }
    
    public Item saveOne(Item item) {
    	
    	return itemRepository.insert(item);
    }
    
    public Item updatedItemByid(Item item) {
    	  // return itemRepository.save(item);
    	itemRepository.deleteById(item.getId());
    	 return itemRepository.save(item);
    }
    //productId, int quantity, Location location
    public Item findById(String id) {
    	Optional<Item> opt =itemRepository.findById(id);
    	
    	return opt.isPresent() ? opt.get() : opt.orElse(new Item(id, 0, null));
    }
    
    public void deleteById(String id) {
    
    	itemRepository.deleteById(id);
    	
    }
    
    public List<Item> findbyProductId(String productId) {
    	
    	return itemRepository.finditemsByProductId(productId);
    }
    
    public void updateItemQuantity(String nid, int num) {
    	
    	  Optional<Item> item = itemRepository.findById(nid);
    	  
    	  if(item.isPresent()) {
    		 Item aitem =item.get();
    		  int change=aitem.getQuantity() - num;
    		  if (change >-1) {
    		  aitem.setQuantity(change);
    		  itemRepository.save(aitem);
    		  }
    		  else {
    			  aitem.setQuantity(0);
    			  itemRepository.save(aitem);
    		  }
    		  
    		  System.out.println("The Updated Item:  "+aitem.toString());
    	  }
    	
    }
}
