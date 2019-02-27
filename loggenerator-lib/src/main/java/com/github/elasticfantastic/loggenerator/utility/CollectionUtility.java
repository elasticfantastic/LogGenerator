package com.github.elasticfantastic.loggenerator.utility;

import java.util.Collection;

public class CollectionUtility {

	public static <E> E getRandom(Collection<E> collection) {
		return collection.stream().skip((int) (collection.size() * Math.random())).findFirst().orElse(null);
	}

}
