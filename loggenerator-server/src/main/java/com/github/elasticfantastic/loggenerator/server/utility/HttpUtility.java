package com.github.elasticfantastic.loggenerator.server.utility;

import org.springframework.http.HttpStatus;

public class HttpUtility {

    public static HttpStatus getStatus(String level) {
        switch (level) {
            case "ERROR":
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.OK;
        }
    }
	
}
