package com.everis.customerservice.service;

import com.everis.customerservice.entity.Customer;

import reactor.core.publisher.Mono;


public interface ICustomerService extends IMaintenanceService<Customer> {

	Mono<Customer> findByNumDoc(String numDoc);
	Mono<Customer> findByPhoneNumDebit(String phone);
}
