package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Product Not Found") //404
public class NoSuchElementFoundException extends RuntimeException{
	private static final long serialVersionUID = -3332292346834265371L;
	public NoSuchElementFoundException(String id) {
		super("ProductNotFoundException with id="+id);
	}

}
