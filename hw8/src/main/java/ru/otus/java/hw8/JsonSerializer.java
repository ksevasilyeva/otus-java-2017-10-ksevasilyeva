package ru.otus.java.hw8;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class JsonSerializer {

    public String toJson(Object object) {

        if (object == null) {
            return "{}";
        }

        StringBuilder resultString = new StringBuilder("{");

        String elemSeparator = "";
        for (Field field : object.getClass().getDeclaredFields()) {
            Object fieldValue;
            try {
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
                .append(appendValue(fieldValue));
            elemSeparator = ",";
        }

        return resultString.append('}').toString();
    }

    public String appendValue(Object fieldValue) {

        if (fieldValue instanceof Number ||
            fieldValue instanceof Boolean) {
            return fieldValue.toString();
        }

        if (fieldValue instanceof String) {
            return String.format("\"%s\"", fieldValue);
        }

        if (fieldValue.getClass().isArray()) {
            StringBuilder resultString = new StringBuilder("[");
            for (int i = 0; i < Array.getLength(fieldValue); i++) {
                resultString.append(appendValue(Array.get(fieldValue, i)));
                if (i != Array.getLength(fieldValue) - 1) {
                    resultString.append(",");
                }
            }
            return resultString.append("]").toString();
        }

        if (fieldValue instanceof Collection) {
            StringBuilder sb = new StringBuilder("[");
            String elemSeparator = "";
            for (Object value : (Collection) fieldValue) {
                sb.append(elemSeparator)
                    .append(appendValue(value));
                elemSeparator = ",";
            }
            return sb.append("]").toString();
        }

        if (fieldValue instanceof Object) {
            return toJson(fieldValue);
        }
        return "{}";
    }
}
