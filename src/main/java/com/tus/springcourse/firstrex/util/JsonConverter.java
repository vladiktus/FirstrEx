package com.tus.springcourse.firstrex.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.springcourse.firstrex.model.Role;

import java.io.IOException;
import java.util.List;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<Role> getListOfRoles(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Role>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object convertJsonToObject(String json, Class clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
