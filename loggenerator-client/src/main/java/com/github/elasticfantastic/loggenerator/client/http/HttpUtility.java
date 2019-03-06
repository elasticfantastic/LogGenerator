package com.github.elasticfantastic.loggenerator.client.http;

public class HttpUtility {

	public static String toLogLevel(int responseCode) {
		if (200 <= responseCode && responseCode <= 299) {
			return "INFO";
		}
		return "ERROR";
	}

}
