package com.github.elasticfantastic.loggenerator.core.utility.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility class for JSON operations.
 * 
 * @author Daniel Nilsson
 */
public class JsonUtility {

    private static ObjectMapper mapper;

    /**
     * Static constructor for creation of JSON mapper.
     */
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * Converts the specified object to JSON.
     * 
     * @param obj
     *            the object to convert
     * @return a JSON representation of the specified object
     * @throws JsonProcessingException
     *             if the object couldn't be converted to JSON
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
