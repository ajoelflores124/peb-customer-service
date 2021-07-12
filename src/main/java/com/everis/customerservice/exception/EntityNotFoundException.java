package com.everis.customerservice.exception;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String message) {
		super(message);
		//System.out.println("Error manejado");
	}
}
