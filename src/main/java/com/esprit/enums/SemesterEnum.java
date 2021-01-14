package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SemesterEnum {

	S1, S2;

	private static Map<String, SemesterEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("S1", S1);
		namesMap.put("S2", S2);
	}

	@JsonCreator
	public static SemesterEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(SemesterEnum value) {
		String key = null;
		for (Map.Entry<String, SemesterEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, SemesterEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}