package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class which holds parameters and their respective values.
 * 
 * @author Daniel Nilsson
 */
public class ParameterContainer {

	private static Map<String, String> parameters;

	static {
		parameters = new HashMap<>();
	}

	public static void putParameter(String key, String value) {
		parameters.put(key, value);
	}

	public static String getParameter(String key) {
		return parameters.get(key);
	}

}
