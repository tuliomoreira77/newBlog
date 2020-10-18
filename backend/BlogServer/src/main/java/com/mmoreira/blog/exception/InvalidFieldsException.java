package com.mmoreira.blog.exception;

public class InvalidFieldsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2527719993528812791L;
	
	public InvalidFieldsException() {
		super("Campos inválidos");
	}
	public InvalidFieldsException(String message) {
		super(message);
	}
}
