package com.everis.customerservice.topic.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.everis.customerservice.entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomerServiceProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	private final static String CUSTOMER_TOPIC = "customerservice-topic";
	
	public void sendSaveCustomerService(Customer customer) {
		log.info("enviando el objeto cliente");
		kafkaTemplate.send(CUSTOMER_TOPIC, customer );
	}
}
