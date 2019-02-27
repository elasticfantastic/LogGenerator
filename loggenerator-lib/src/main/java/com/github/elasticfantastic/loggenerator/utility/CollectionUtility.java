package com.github.elasticfantastic.loggenerator.utility;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CollectionUtility {

	public static <E> E getRandom(Collection<E> collection) {
		return collection.stream().skip((int) (collection.size() * Math.random())).findFirst().orElse(null);
	}

	public static <E> List<E> getRandom(List<E> list, int size) {
		Collections.shuffle(list, new Random());
		return list.stream().limit(size).collect(Collectors.toList());
	}

}
