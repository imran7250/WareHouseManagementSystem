package com.nt.service.exception;

public class ShipmentTypeNotFoundException extends RuntimeException {
	
	private static final long SerialVersionUID =1L;
	
    public  ShipmentTypeNotFoundException() {
		 super();    
	}
    public ShipmentTypeNotFoundException(String message) {
    	 super(message);   
    }
}
