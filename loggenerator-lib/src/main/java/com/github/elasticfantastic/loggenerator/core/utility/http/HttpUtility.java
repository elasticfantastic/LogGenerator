package com.github.elasticfantastic.loggenerator.core.utility.http;

import org.springframework.http.HttpStatus;

/**
 * Utility class for HTTP operations.
 * 
 * @author Daniel Nilsson
 */
public class HttpUtility {

    /**
     * Get the level equivalent to a certain response code.
     * 
     * @param responseCode
     *            the response code
     * @return the level matching the response code
     */
    public static String toLevel(int responseCode) {
        if (200 <= responseCode && responseCode <= 299) {
            return "INFO";
        }
        return "ERROR";
    }

    /**
     * Get the HTTP status equivalent of a certain level.
     * 
     * @param level
     *            the level
     * @return the HTTP status matching the level
     */
    public static HttpStatus toStatus(String level) {
        switch (level) {
            case "ERROR":
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.OK;
        }
    }

}
