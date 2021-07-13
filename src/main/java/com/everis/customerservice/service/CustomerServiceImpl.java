package com.everis.customerservice.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.exception.EntityNotFoundException;
import com.everis.customerservice.redis.document.CustomerType;
import com.everis.customerservice.redis.service.CustomerTypeService;
import com.everis.customerservice.repository.ICustomerRepository;
import com.everis.customerservice.topic.producer.CustomerServiceProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@PropertySource("classpath:application.properties")
@Service
public class CustomerServiceImpl implements ICustomerService{

	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	@Autowired
	private ICustomerRepository customerRep;
	private final ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	private CustomerTypeService  customerTypeService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    public CustomerServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Autowired
	CustomerServiceProducer customerServiceProducer;
	
	WebClient webClient = WebClient.create(urlCustomerService);
	
	@Override
	public Flux<Customer> findAll() {
		return customerRep.findAll();
	}
	
	@Override
	public Mono<Customer> findEntityById(String id) {
		return customerRep.findById(id);
	}

	@Override
	public Mono<Customer> createEntity(Customer customer) throws Exception {
	
	   //Obtenemos la descripcion del tipo de cliente desde REDIS
	   Object obj=customerTypeService.get(customer.getTypeCustomer()).share().block();
	   final ObjectMapper mapper = new ObjectMapper();
	   final CustomerType pojo = mapper.convertValue((Map<String,CustomerType>)obj, CustomerType.class);
	   //System.out.println( "customerType=>"+pojo.getTypeDes() );
	   customer.setTypeCustomer( pojo==null?"P":pojo.getTypeDes());
	   customerServiceProducer.sendSaveCustomerService(customer);
	   return customerRep.insert(customer);
	}

	@Override
	public Mono<Customer> updateEntity(Customer customer) {
		return  customerRep.findById(customer.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> customerRep.save(customer));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  customerRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> customerRep.deleteById(id));
	}

	@Override
	public Mono<Customer> findByNumDoc(String numDoc) {
		return  customerRep.findByNumDoc(numDoc)
				.switchIfEmpty(Mono.error(new EntityNotFoundException(msgNotFound)) );
				 
				 
	}

	@Override
	public Mono<Customer> findByPhoneNumDebit(String phone) {
		Query query= new Query(Criteria.where("cardNumDebit").ne(null)
				.andOperator(
						Criteria.where("phone").is(phone),
						new Criteria().orOperator(
								Criteria.where("typeCustomer").is("Yanki"),
								Criteria.where("typeCustomer").is("Y")
								)
						)
				);
		return mongoTemplate.findOne(query,Customer.class);
	}

}
