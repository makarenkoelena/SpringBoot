package com.sales.exceptions;

public class NoSuchLoanException extends RuntimeException{
	public NoSuchLoanException(String message){
		super(message);
	}
}
