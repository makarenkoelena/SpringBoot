package com.sales.exceptions;

public class BookOnLoanException extends RuntimeException{
	public BookOnLoanException(String message){
		super(message);
	}
}
