package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Empty_Collection")
public class EmptyException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EmptyException(String data) {
		super("The collection is empty");
		System.out.println("runtime exception: "+data);
	}

}
