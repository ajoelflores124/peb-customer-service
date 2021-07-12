package com.everis.customerservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.customerservice.entity.Customer;

import reactor.core.publisher.Mono;

@Repository
public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String>{

	Mono<Customer> findByNumDoc(String numDoc);
	
}
