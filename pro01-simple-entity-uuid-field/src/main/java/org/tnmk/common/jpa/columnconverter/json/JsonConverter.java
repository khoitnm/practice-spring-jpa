package org.tnmk.common.jpa.columnconverter.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This is an abstract class to help serialize/deserialize complex objects into stringed JSON column
 * <p>
 * To implement this just extends this class and specify your types.
 */
public abstract class JsonConverter<T> implements AttributeConverter<T, String> {
    //TODO it should be injected
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * This makes the deserialization process drastically easier
     */
    private final JavaType targetJavaType;

    protected JsonConverter() {
        this.targetJavaType = getTargetJavaType();
    }

    private JavaType getTargetJavaType() {
        ParameterizedType superParameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type targetType = superParameterizedType.getActualTypeArguments()[0];

        if (targetType instanceof ParameterizedType) {
            return constructJavaType(OBJECT_MAPPER, (ParameterizedType) targetType);
        } else if (targetType instanceof Class) {
            return OBJECT_MAPPER.getTypeFactory().constructType(targetType);
        } else {
            throw new IllegalArgumentException("The argument type should be either ParameterizedType or Class. But the actual type is " + targetType);
        }
    }

    @Override
    public String convertToDatabaseColumn(T object) {
        String jsonString;
        try {
            jsonString = OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConversionException("Cannot convert to type", e);
        }
        return jsonString;
    }

    @Override
    public T convertToEntityAttribute(String string) {
        T object;
        try {
            object = OBJECT_MAPPER.readValue(string, targetJavaType);
        } catch (IOException e) {
            throw new JsonConversionException("Cannot convert to type", e);
        }
        return object;
    }

    private JavaType constructJavaType(ObjectMapper objectMapper, ParameterizedType parameterizedType){
        Class rawType = (Class) parameterizedType.getRawType();
        Type[] argumentTypes = parameterizedType.getActualTypeArguments();
        Class[] argumentJavaTypes = toClasses(argumentTypes);
        return objectMapper.getTypeFactory().constructParametricType(rawType, argumentJavaTypes);
    }

    private Class[] toClasses(Type[] types){
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }
        return classes;
    }
}
