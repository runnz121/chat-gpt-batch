package com.example.gpt.demo.infra.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RequiredArgsConstructor
public class FileToObject {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fileToObject(String filePath,
                                     Class<T> clazz) throws JsonProcessingException {
        String payload = asString(filePath);
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (Exception ex) {
            log.error("[error]", ex);
        }
        return null;
    }

    public static <T> List<T> fileToList(String filePath,
                                         Class<T> clazz) throws JsonProcessingException {
        String payload = asString(filePath);
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return objectMapper.readValue(payload, type);
        } catch (Exception ex) {
            log.error("[error]", ex);
        }
        return null;
    }

    public static String asString(Resource resource) {

        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String asString(String classPath) {

        return asString(new ClassPathResource(classPath));
    }
}
