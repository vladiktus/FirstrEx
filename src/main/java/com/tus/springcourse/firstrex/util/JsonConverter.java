package com.tus.springcourse.firstrex.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.model.RoleOld;
import com.tus.springcourse.firstrex.model.db.ArtistDb;

/**
 * Utility class for converting JSON strings to objects and vice versa.
 */
public class JsonConverter {
    /**
     * Utility class for converting JSON strings to objects and vice versa.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Converts a JSON string to a list of Role objects.
     *
     * @param json the JSON string representing a list of roles
     * @return a list of Role objects
     * @throws RuntimeException if the JSON processing fails
     */
    public static List<RoleOld> getListOfRoles(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<RoleOld>>() {
            });
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Genre> getListOfGenres(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<Genre>>() {
            });
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ArtistDb> getListOfArtistDb(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<ArtistDb>>() {
            });
        }
        catch (JsonProcessingException e) {
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
            return OBJECT_MAPPER.readValue(json, clazz);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
