package com.tus.springcourse.firstrex.util;

import com.tus.springcourse.firstrex.model.RoleOld;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterTest {
    private static JsonConverter jsonConverter;

    @BeforeAll
    static void setUp() {
        jsonConverter = new JsonConverter();
    }

    @Test
    public void seccussesTest() throws IOException {
        List<RoleOld> expectedValue = List.of(RoleOld.builder().id(1).name("Artist").build(),
                RoleOld.builder().id(2).name("Director").build());
        List<RoleOld> actualValue = jsonConverter.getListOfRoles("[{\"id\": 1, \"name\": \"Artist\"}, {\"id\": 2, \"name\": \"Director\"}]");
        assertThat(expectedValue).hasSameElementsAs(actualValue);
    }
}