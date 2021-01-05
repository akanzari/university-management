package com.esprit.error.exception;

public class EntityAlreadyExistsExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistsExeption(Object o, String field, String id) {
		super(o + " already exists with " + field + " [" + id + "]");
	}

}