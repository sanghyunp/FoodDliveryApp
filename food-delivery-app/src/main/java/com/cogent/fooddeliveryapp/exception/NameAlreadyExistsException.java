package com.cogent.fooddeliveryapp.exception;

public class NameAlreadyExistsException extends Exception {
	public NameAlreadyExistsException(String e) {
		super(e);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
	
	
}
