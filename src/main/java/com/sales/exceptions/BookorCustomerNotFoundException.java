package com.sales.exceptions;

public class BookorCustomerNotFoundException extends RuntimeException{
	public BookorCustomerNotFoundException(String message){
		super(message);
	}
}
