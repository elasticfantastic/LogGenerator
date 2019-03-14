package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.Base64;

/**
 * Utility methods for Base64 operations.
 * 
 * @author Daniel Nilsson
 */
public class Base64Utility {

    /**
     * Returns true if the specified string is encoded in Base64, otherwise false.
     * 
     * @param str
     *            the specified string to check
     * @return true if the specified string is encoded in Base64, otherwise false
     */
    public static boolean isEncoded(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(str);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

}
