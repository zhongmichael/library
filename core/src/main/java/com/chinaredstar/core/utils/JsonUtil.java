package com.chinaredstar.core.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/7/31.
 */

public class JsonUtil {
    private static ObjectMapper mapper;

    private JsonUtil() {
    }

    @Nullable
    public static String toJsonString(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <T> T parse(String jsonString, Class<T> clsBean) {
        try {
            return getObjectMapper().readValue(jsonString, clsBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonString, getCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            synchronized (JsonUtil.class) {
                if (mapper == null) {
                    mapper = new ObjectMapper();
                    mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
                    mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.setSerializationInclusion(Inclusion.NON_NULL);
                }
            }
        }
        return mapper;
    }
}
