package com.spotify.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperUtils {

    public static <T> T convertValue(Object object, Class<T> finalClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, finalClass);
    }
}
