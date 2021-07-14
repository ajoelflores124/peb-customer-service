package com.everis.customerservice.topic.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.repository.ICustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomerServiceConsumer {

	private final static String YANKI_TOPIC = "yankiservice-topic";
	private final static String BOOTCOIN_TOPIC = "p2pservice-topic";
	private final static String GROUP_ID = "customer-group";
	
	@Autowired
	private ICustomerRepository customerRep;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@KafkaListener( topics = YANKI_TOPIC, groupId = GROUP_ID)
	public Disposable retrieveSavedCustomer(String data) throws Exception {
		log.info("data desde kafka listener (customer) =>"+data);
		Customer customer= objectMapper.readValue(data, Customer.class );
		 
		return Mono.just(customer)
		        .log()
		        .flatMap(customerRep::save)
		        .subscribe();
	}
	
	@KafkaListener( topics = BOOTCOIN_TOPIC, groupId = GROUP_ID)
	public Disposable retrieveSavedCustomerBootcoin(String data) throws Exception {
		log.info("data desde kafka listener ( bootcoin customer) =>"+data);
		Customer customer= objectMapper.readValue(data, Customer.class );
		 
		return Mono.just(customer)
		        .log()
		        .flatMap(customerRep::save)
		        .subscribe();
	}
	
}
