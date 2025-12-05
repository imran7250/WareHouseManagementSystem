package com.nt.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nt.service.exception.PartNotFoundException;
import com.nt.service.exception.UomNotFoundException;

@RestControllerAdvice
public class AppErrorHandler {

	@ExceptionHandler(UomNotFoundException.class)
	public ResponseEntity<String> handleUomNotFoundEntity(UomNotFoundException unfe){
		return new ResponseEntity<>(unfe.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PartNotFoundException.class)
	public ResponseEntity<String> handlePartNotFoundEntity(PartNotFoundException unfe){
		return new ResponseEntity<>(unfe.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
