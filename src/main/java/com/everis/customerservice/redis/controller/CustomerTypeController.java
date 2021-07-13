package com.everis.customerservice.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.customerservice.redis.document.CustomerType;
import com.everis.customerservice.redis.service.CustomerTypeService;


import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/customer-type")
public class CustomerTypeController {
	
	@Autowired
	private CustomerTypeService  customerTypeService;
	
	@PostMapping
    public Mono<Boolean> put(@RequestBody CustomerType prod) {
        return customerTypeService.put(prod.getId(), prod);
    }
	
	@GetMapping("/{key}")
    public Mono<Object> get(@PathVariable("key") String key) {
        return customerTypeService.get(key);
    }
	
	@DeleteMapping("/{key}")
    public Mono<Boolean> delete(@PathVariable("key") String key) {
        return customerTypeService.delete(key);
    }
	

}
