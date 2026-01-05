package io.wulfcodes.serdes.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    // Static Initializer for Global Configuration
    static {
        OBJECT_MAPPER = new ObjectMapper();

        // Handle Java 8 Dates (LocalDate, Instant, etc.)
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Ignore fields in JSON that strictly aren't in the Java Class
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Ignore null fields when writing JSON (Reduces payload size)
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // Private constructor to prevent instantiation
    private JsonUtils() {
    }

    /**
     * Get the raw OBJECT_MAPPER if deeper customization is needed
     */
    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    // ============================
    // 1. Object -> JSON String
    // ============================

    public static String toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    // ============================
    // 2. JSON String -> Object
    // ============================

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    // ============================
    // 3. JSON String -> Complex Types (List<T>, Map<K,V>)
    // ============================

    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }


    // ============================
    // 4. File Operations
    // ============================

    public static <T> T fromFile(String filePath, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(new File(filePath), clazz);
    }

    public static void toFile(String filePath, Object object) throws IOException {
        OBJECT_MAPPER.writeValue(new File(filePath), object);
    }
}