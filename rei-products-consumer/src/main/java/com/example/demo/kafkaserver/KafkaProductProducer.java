package com.example.demo.kafkaserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.module.*;
@Service
@CrossOrigin("*")
public class KafkaProductProducer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProductProducer.class);
	
	private static final String TOPIC = "evnproduct";
	
	@Autowired
	private KafkaTemplate<String,Object> productkafkaTemplate;

    public void sendMessage(String message){
    logger.info(String.format("$$ -> Producing message --> %s",message));
   this.productkafkaTemplate.send(TOPIC,message);
    }


    public void sendMessage(Item item){
    logger.info(String.format("$$ -> Producing message --> %s",item));
//BookEvent ebook=new BookEvent(
    this.productkafkaTemplate.send(TOPIC, item);
    }
}
