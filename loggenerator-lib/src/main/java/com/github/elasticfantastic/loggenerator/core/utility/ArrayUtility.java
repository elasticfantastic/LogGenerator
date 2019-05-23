package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.Random;

/**
 * Utility methods for array operations.
 * 
 * @author Daniel Nilsson
 */
public class ArrayUtility {

    /**
     * Get a random element from the specified array.
     * 
     * @param arr
     *            the array to get a random element from
     * @return a random element
     */
    public static <E> E getRandom(E[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

}
