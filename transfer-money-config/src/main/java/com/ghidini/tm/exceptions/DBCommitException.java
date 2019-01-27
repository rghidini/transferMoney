package com.ghidini.tm.exceptions;

public class DBCommitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBCommitException(Object entity) {
		super("An error has occurred while trying to commit " + entity.toString());
	}

	public DBCommitException() {}

}
