package com.nt.service.exception;

public class UomNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID=1L;
	
	public UomNotFoundException() {
		super();
	}
	public UomNotFoundException(String message) {
		super(message);
	}
}
