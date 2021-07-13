package com.everis.customerservice.redis.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import lombok.Data;


@Data
public class CustomerType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String typeDes;
	private long status;

}
