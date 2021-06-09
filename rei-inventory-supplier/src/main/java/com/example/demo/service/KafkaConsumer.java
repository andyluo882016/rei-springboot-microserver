package com.example.demo.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.module.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@Service
public class KafkaConsumer {
   @Autowired
	InventoryService inventoryService;
	
	private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	@KafkaListener(topics = "evnproduct", groupId = "product-group", containerFactory = "itemkafkaListener")
	public void consume(Item item) throws JsonParseException, JsonMappingException, IOException{
		System.out.println("\n--->>>>>  "+item);
		inventoryService.updatedItemByid(item);
	logger.info(String.format("$$ -> Consumed Message -> %s",item));
	
	}
}
