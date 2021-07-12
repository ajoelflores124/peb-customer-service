package com.everis.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.service.CustomerServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;
	
	@GetMapping
	public Flux<Customer> getCustomers(){
		return customerService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Customer> getCustomer(@PathVariable String id){
		return customerService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<Customer> saveCustomer(@RequestBody Customer customerMono){
		return customerService.createEntity(customerMono);
	}
	
	@PutMapping
	public Mono<Customer> updateCustomer(@RequestBody Customer customerMono){
		return customerService.updateEntity(customerMono);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteCustomer(@PathVariable String id){
		return customerService.deleteEntity(id);
	}
	
	@GetMapping("/find-by-numdoc/{numDoc}")
	public Mono<Customer> getCustomerByNumDoc(@PathVariable String numDoc){
		return customerService.findByNumDoc(numDoc);
	}
	
	@GetMapping("/find-by-phone/{phone}")
	public Mono<Customer> getCustomerByPhone(@PathVariable String phone){
		return customerService.findByPhoneNumDebit(phone);
	}
}
