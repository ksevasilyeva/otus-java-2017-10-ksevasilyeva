package ru.otus.java.hw8;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class JsonSerializer {

    public String toJson(Object object) {

        if (object == null) {
            return "null";
        }

        if (object instanceof String) {
            return String.format("\"%s\"", object);
        }

        if (object instanceof Number ||
            object instanceof Boolean) {
            return object.toString();
        }

        if (object.getClass().isArray()) {
            return appendArray(object);
        }

        if (object instanceof Collection) {
            return appendCollection(object);
        }

        try {
            return appendObject(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed serializing", e);
        }
    }

    public String appendObject(Object object) {
        StringBuilder resultString = new StringBuilder("{");

        String elemSeparator = "";
        for (Field field : object.getClass().getDeclaredFields()) {
            Object fieldValue;
            try {
                field.setAccessible(true);
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            if (fieldValue == null) {
                continue;
            }
            resultString.append(elemSeparator)
                .append(String.format("\"%s\":", field.getName()))
                .append(toJson(fieldValue));
            elemSeparator = ",";
        }

        return resultString.append('}').toString();
    }

    public String appendArray(Object fieldValue) {
        StringBuilder resultString = new StringBuilder("[");
        for (int i = 0; i < Array.getLength(fieldValue); i++) {
            resultString.append(toJson(Array.get(fieldValue, i)));
            if (i != Array.getLength(fieldValue) - 1) {
                resultString.append(",");
            }
        }
        return resultString.append("]").toString();
    }

    public String appendCollection(Object fieldValue) {
        StringBuilder sb = new StringBuilder("[");
        String elemSeparator = "";
        for (Object value : (Collection) fieldValue) {
            sb.append(elemSeparator)
                .append(toJson(value));
            elemSeparator = ",";
        }
        return sb.append("]").toString();
    }
}
