package com.nt.service.exception;

public class PurchaseOrderNotFound extends RuntimeException {
 
	public PurchaseOrderNotFound() {
		
	}	
	public PurchaseOrderNotFound(String message) {
		super(message);
	}
}
