package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

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
		return namesMap.get(StringUtils.lowerCase(value));
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