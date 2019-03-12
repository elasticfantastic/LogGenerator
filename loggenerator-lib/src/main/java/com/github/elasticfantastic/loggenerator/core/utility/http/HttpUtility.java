package com.github.elasticfantastic.loggenerator.core.utility.http;

import org.springframework.http.HttpStatus;

public class HttpUtility {

    public static String toLevel(int responseCode) {
        if (200 <= responseCode && responseCode <= 299) {
            return "INFO";
        }
        return "ERROR";
    }

    public static HttpStatus toStatus(String level) {
        switch (level) {
            case "ERROR":
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.OK;
        }
    }

}
