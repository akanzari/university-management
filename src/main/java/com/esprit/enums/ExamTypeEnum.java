package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExamTypeEnum {

	WRITTEN, PRACTICE, PRESENTATION;

	private static Map<String, ExamTypeEnum> namesMap = new HashMap<>(2);

	static {
		namesMap.put("Écrit", WRITTEN);
		namesMap.put("Pratique", PRACTICE);
		namesMap.put("Soutenance", PRESENTATION);
	}

	@JsonCreator
	public static ExamTypeEnum forValue(String value) {
		return namesMap.get(StringUtils.lowerCase(value));
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