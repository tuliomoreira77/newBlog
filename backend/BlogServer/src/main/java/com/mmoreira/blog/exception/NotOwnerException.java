package com.mmoreira.blog.exception;

public class NotOwnerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5671503741620971568L;
	
	public NotOwnerException() {
		super("Usuário não é dono da entidade");
	}
	public NotOwnerException(String message) {
		super(message);
	}
}
