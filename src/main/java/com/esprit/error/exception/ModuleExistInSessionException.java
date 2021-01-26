package com.esprit.error.exception;

public class ModuleExistInSessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModuleExistInSessionException(String msg) {
		super(msg);
	}

}