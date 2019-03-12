package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.Base64;

public class Base64Utility {

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
