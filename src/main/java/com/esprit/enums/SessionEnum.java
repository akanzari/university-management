package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionEnum {

	PRINCIPAL, REMEDIAL;

	private static Map<String, SessionEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("Principal", PRINCIPAL);
		namesMap.put("Rattrapage", REMEDIAL);
	}

	@JsonCreator
	public static SessionEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(SessionEnum value) {
		String key = null;
		for (Map.Entry<String, SessionEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, SessionEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}