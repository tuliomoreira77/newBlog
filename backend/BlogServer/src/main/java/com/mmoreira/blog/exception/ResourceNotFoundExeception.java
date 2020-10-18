package com.mmoreira.blog.exception;

public class ResourceNotFoundExeception extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundExeception() {
		super("Entidade não encontrada");
	}
	public ResourceNotFoundExeception(String string) {
		super(string);
	}
}
