package com.farm.console.contain.exception;

public class UserNoExistException extends Exception {
	private static final long serialVersionUID = 1L;
	public  UserNoExistException(String messager){
		super(messager);
	}
}
