package com.github.elasticfantastic.loggenerator.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class CollectionUtility {

	public static <E> E getRandom(List<E> list) {
		return getRandom(list, 1).get(0);
	}

	public static <E> List<E> getRandom(List<E> list, int size) {
		return getRandom(list, size, new HashMap<>());
	}

	public static <E> List<E> getRandom(List<E> list, int size, Map<E, Integer> loadFactors) {
		Random random = new Random();
		if (loadFactors.isEmpty()) {
			Collections.shuffle(list, random);
			return list.stream().limit(size).collect(Collectors.toList());
		}
		List<E> loadFactorsList = new ArrayList<>();
		int currentPos = 0;
		for (E element : list) {
			int loadFactor = loadFactors.get(element);
			int end = currentPos + loadFactor;
			for (int i = currentPos; i < end; i++) {
				loadFactorsList.add(element);
				currentPos++;
			}
		}
		List<E> elements = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			E randomElement = loadFactorsList.get(random.nextInt(loadFactorsList.size()));
			loadFactorsList.removeAll(Arrays.asList(randomElement));
			elements.add(randomElement);
		}
		return elements;
	}

}
