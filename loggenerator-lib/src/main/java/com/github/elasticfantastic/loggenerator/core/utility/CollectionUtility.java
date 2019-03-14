package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Utility methods for collection operations. Also contains operations for subclasses,
 * such as lists.
 * 
 * @author Daniel Nilsson
 */
public class CollectionUtility {

    /**
     * Get a random element from the specified list.
     * 
     * @param list
     *            the list to get a random element from
     * @return a random element
     */
    public static <E> E getRandom(List<E> list) {
        return getRandom(list, 1).get(0);
    }

    /**
     * Get N random element from the specified list, where N equals parameter
     * <code>size</code>.
     * 
     * @param list
     *            the list to get a random element from
     * @param size
     *            the amount of elements to get
     * @return a collection of random elements
     */
    public static <E> List<E> getRandom(List<E> list, int size) {
        return getRandom(list, size, new HashMap<>());
    }

    /**
     * Get N random element from the specified list, where N equals parameter
     * <code>size</code>. Also takes into considerations each element's load factor
     * 
     * @param list
     *            the list to get a random element from
     * @param size
     *            the amount of elements to get
     * @param loadFactors
     *            the load factors
     * @return a collection of random elements
     */
    public static <E> List<E> getRandom(List<E> list, int size, Map<E, Integer> loadFactors) {
        Random random = new Random();
        if (loadFactors.isEmpty()) {
            Collections.shuffle(list, random);
            return list.stream().limit(size).collect(Collectors.toList());
        }
        // Convert the load factors to a list
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
        // For 0 to N, get random elements from the load factors list. After each
        // retrieval, remove all occurances of the randomized element so we don't get
        // duplicates
        for (int i = 0; i < size; i++) {
            E randomElement = loadFactorsList.get(random.nextInt(loadFactorsList.size()));
            loadFactorsList.removeAll(Arrays.asList(randomElement));
            elements.add(randomElement);
        }
        return elements;
    }

}
