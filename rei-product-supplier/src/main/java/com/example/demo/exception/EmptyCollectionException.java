package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Empty_Collection")
public class EmptyCollectionException extends RuntimeException{

private static final long serialVersionUID = 7127811147587857487L;
	
	public EmptyCollectionException(String msg) {
		super("The collection is empty");
	}
}
