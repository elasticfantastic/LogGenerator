package com.github.elasticfantastic.loggenerator.utility;

public class MessageUtility {

	public static String getMessage(String level, String id) {
		switch (level) {
		case "ERROR":
			return id + " doesn't have enough funds";
		case "DEBUG":
			return "Running some server maintenance...";
		case "WARN":
			return "Something is about to go wrong!";
		default:
			return "User " + id + " standard message";
		}
	}

}
