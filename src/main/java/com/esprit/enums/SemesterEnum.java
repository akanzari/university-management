package com.esprit.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

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
		return namesMap.get(StringUtils.lowerCase(value));
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