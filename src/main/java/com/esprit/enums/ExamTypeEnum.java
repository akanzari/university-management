package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExamTypeEnum {

	WRITTEN, PRACTICE, PRESENTATION;

	private static Map<String, ExamTypeEnum> namesMap = new HashMap<>(3);

	static {
		namesMap.put("Écrit", WRITTEN);
		namesMap.put("Pratique", PRACTICE);
		namesMap.put("Soutenance", PRESENTATION);
	}

	@JsonCreator
	public static ExamTypeEnum forValue(String value) {
		return namesMap.get(value);
	}

	public static String forKey(ExamTypeEnum value) {
		String key = null;
		for (Map.Entry<String, ExamTypeEnum> entry : namesMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@JsonValue
	public String toValue() {
		for (Entry<String, ExamTypeEnum> entry : namesMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}

}