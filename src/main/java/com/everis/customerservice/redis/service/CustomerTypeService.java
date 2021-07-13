package com.everis.customerservice.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import com.everis.customerservice.redis.document.CustomerType;
import reactor.core.publisher.Mono;

@Service
public class CustomerTypeService implements IRedisMaintenance<CustomerType> {

	 @Autowired
	 private ReactiveRedisTemplate<String,Object> redisTemplate;
	 
	 @Override
	 public Mono<Boolean> put(String key, CustomerType typeCustomer) {
		 return redisTemplate.opsForValue().set(key, typeCustomer);
	 }

	 public Mono<Object> get(String key) {
		 return redisTemplate.opsForValue().get(key);
	 }

	 public Mono<Boolean> delete(String key) {
		 return redisTemplate.opsForValue().delete(key);
	 }
	
}
