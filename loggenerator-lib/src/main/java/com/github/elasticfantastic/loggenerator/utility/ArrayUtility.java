package com.github.elasticfantastic.loggenerator.utility;

import java.util.Random;

public class ArrayUtility {

	public static String getRandom(String[] arr) {
		int rnd = new Random().nextInt(arr.length);
		return arr[rnd];
	}
	
}
