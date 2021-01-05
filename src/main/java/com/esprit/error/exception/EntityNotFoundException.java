package com.esprit.error.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Object o, String field, String id) {
		super(o + " not found with " + field + " [" + id + "]");
	}
}