package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PeriodEnum {

	P1, P2;

	private static Map<String, PeriodEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("P1", P1);
		namesMap.put("P2", P2);
	}

	@JsonCreator
	public static PeriodEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(PeriodEnum value) {
		String key = null;
		for (Map.Entry<String, PeriodEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, PeriodEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}