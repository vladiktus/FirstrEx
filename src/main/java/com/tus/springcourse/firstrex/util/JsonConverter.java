package com.tus.springcourse.firstrex.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.springcourse.firstrex.model.Role;

import java.io.IOException;
import java.util.List;

/**
 * Utility class for converting JSON strings to objects and vice versa.
 */
public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a JSON string to a list of Role objects.
     *
     * @param json the JSON string representing a list of roles
     * @return a list of Role objects
     * @throws RuntimeException if the JSON processing fails
     */
    public static List<Role> getListOfRoles(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Role>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a JSON string to an object of the specified class type.
     *
     * @param json the JSON string to be converted
     * @param clazz the class type to convert the JSON string to
     * @return an object of the specified class type
     * @throws RuntimeException if the JSON processing fails
     */
    public Object convertJsonToObject(String json, Class clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
