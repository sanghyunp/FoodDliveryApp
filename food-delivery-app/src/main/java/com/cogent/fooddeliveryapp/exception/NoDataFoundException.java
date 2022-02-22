package com.cogent.fooddeliveryapp.exception;


public class NoDataFoundException extends RuntimeException {

	public NoDataFoundException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
	
}
