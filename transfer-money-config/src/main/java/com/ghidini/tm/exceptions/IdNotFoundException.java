package com.ghidini.tm.exceptions;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNotFoundException(Object entity) {
		super("Account with id " + entity + " does not exist.");
	}

	public IdNotFoundException() {}

}
