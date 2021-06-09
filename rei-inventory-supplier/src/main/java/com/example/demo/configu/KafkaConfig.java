package com.example.demo.configu;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.example.demo.module.Item;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
@EnableKafka
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String, String> StrConsumerFactory() {
	     Map<String, Object> itemConfig =new HashMap<>();
	     
	     itemConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
	     itemConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "product-group");
	     itemConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	     itemConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	   
	      return new DefaultKafkaConsumerFactory<>(itemConfig);
	}
     @Bean
	 public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

	        ConcurrentKafkaListenerContainerFactory<String, String> itemfactory =new ConcurrentKafkaListenerContainerFactory<>();
	        itemfactory.setConsumerFactory(StrConsumerFactory());
	        return itemfactory;

	 }
     //kafkaListenerContainerFactory
     @Bean  //User
     public ConsumerFactory<String, Item> itemconsumerFactory(){
 			
        Map<String, Object> config = new HashMap<>(); //127.0.0.1
         config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
         config.put(ConsumerConfig.GROUP_ID_CONFIG,"product-group");
         config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
 		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
         
         return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),
                 new JsonDeserializer<>(Item.class));
     }

     @Bean
     public ConcurrentKafkaListenerContainerFactory<String, Item> itemkafkaListener(){
         ConcurrentKafkaListenerContainerFactory<String, Item> factory = new ConcurrentKafkaListenerContainerFactory<>();
         factory.setConsumerFactory(itemconsumerFactory());
         return factory;
     }
     
     
}
