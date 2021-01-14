package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DsexEnum {

	DS, EXAM;

	private static Map<String, DsexEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("DS", DS);
		namesMap.put("Examen", EXAM);
	}

	@JsonCreator
	public static DsexEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(DsexEnum value) {
		String key = null;
		for (Map.Entry<String, DsexEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, DsexEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}