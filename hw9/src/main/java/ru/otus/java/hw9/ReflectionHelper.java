package ru.otus.java.hw9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionHelper.class);

    private static List<Field> getFieldsByClass(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz.getSuperclass()!= null) {
            fieldList.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    public static String getFieldsValues(Object object){
        StringBuilder sb = new StringBuilder();
        String elemSeparator = "";
        for (Field field : ReflectionHelper.getFieldsByClass(object.getClass())) {
            Object fieldValue;
            try {
                field.setAccessible(true);
                fieldValue = field.get(object);
            } catch (IllegalAccessException e){
                throw new RuntimeException(e);
            }
            Class<?> fieldType = field.getType();

            String fieldValueToString;
            if (fieldType == String.class || fieldType == Character.class){
                fieldValueToString = "'" + fieldValue + "'";
            } else {
                fieldValueToString = fieldValue.toString();
            }
            sb.append(elemSeparator)
                .append(fieldValueToString);
            elemSeparator = ",";
        }
        return sb.toString();
    }

    public static <T> T newInstanceWithParameters(Class<T> clazz, Object[] parameters){
        try {
            return ReflectionHelper.getConstructorWithFullParameters(clazz)
                .newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Failed to create class instance", e);
        }
        return null;
    }

    public static <T> Constructor<T> getConstructorWithFullParameters(Class<T> clazz) throws NoSuchMethodException {
        List<Class<?>> fieldTypes = new ArrayList<>();
        for (Field field : getFieldsByClass(clazz)) {
            fieldTypes.add(field.getType());
        }
        Class[] array = new Class[fieldTypes.size()];
        return clazz.getConstructor(fieldTypes.toArray(array));
    }
}
