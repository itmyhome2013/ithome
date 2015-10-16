package com.farm.console.contain.exception;

public class UserLoginNameIsExistException extends Exception {
	private static final long serialVersionUID = 1L;
	public  UserLoginNameIsExistException(String messager){
		super(messager);
	}
}
