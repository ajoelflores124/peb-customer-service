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
import com.everis.customerservice.redis.document.DocumentType;
import com.everis.customerservice.redis.service.CustomerTypeService;
import com.everis.customerservice.redis.service.DocumentTypeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/document-type")
public class DocumentTypeController {
	
	@Autowired
	private DocumentTypeService documentTypeService;
	
	@PostMapping
    public Mono<Boolean> put(@RequestBody DocumentType doc) {
        return documentTypeService.put(doc.getId(), doc);
    }
	
	@GetMapping("/{key}")
    public Mono<Object> get(@PathVariable("key") String key) {
        return documentTypeService.get(key);
    }
	
	@DeleteMapping("/{key}")
    public Mono<Boolean> delete(@PathVariable("key") String key) {
        return documentTypeService.delete(key);
    }
	

}
