package com.esprit.error;

import org.springframework.lang.Nullable;

public enum CustomHttpStatus {

	BAD_REQUEST(700, "Bad request"), 
	ENTITY_NOT_FOUND(701, "Entity not found request"), 
	ENTITY_ALREADY_EXISTS(702, "Entity already exists request"), 
	ROOM_SIZE_REQUEST(703, "room size request"), 
	MODULE_EXIST_REQUEST(705, "module exist request"), 
	TEACHER_SIZE_REQUEST(704, "teacher size request");

	private final int value;

	private final String reason;

	CustomHttpStatus(int value, String reason) {
		this.value = value;
		this.reason = reason;
	}

	public int value() {
		return this.value;
	}

	public String reason() {
		return this.reason;
	}

	@Override
	public String toString() {
		return this.value + " " + name();
	}

	public static CustomHttpStatus valueOf(int statusCode) {
		CustomHttpStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		}
		return status;
	}

	@Nullable
	public static CustomHttpStatus resolve(int statusCode) {
		for (CustomHttpStatus status : values()) {
			if (status.value == statusCode) {
				return status;
			}
		}
		return null;
	}

}