package com.nt.service.exception;

public class WhUserTypeNotFoundException extends RuntimeException {
     /**
	 * 
	 */
	private static final long serialVersionUID = -6566102540608145871L;

	public WhUserTypeNotFoundException() {
		super();
	}
     
    public WhUserTypeNotFoundException(String message) {
    	super(message);
    }
}
  