package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.Random;

public class ArrayUtility {

	public static <E> E getRandom(E[] arr) {
		int rnd = new Random().nextInt(arr.length);
		return arr[rnd];
	}

}
