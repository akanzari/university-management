package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PoleEnum {

	C, G;

	private static Map<String, PoleEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("Charguia", C);
		namesMap.put("El Ghazala", G);
	}

	@JsonCreator
	public static PoleEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(PoleEnum value) {
		String key = null;
		for (Map.Entry<String, PoleEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, PoleEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}