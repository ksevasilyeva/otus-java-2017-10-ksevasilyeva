package ru.otus.java.hw8.test;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.java.hw8.JsonSerializer;
import ru.otus.java.hw8.data.ClassWithDataFields;
import ru.otus.java.hw8.data.ClassWithObjectField;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;

public class JsonSerializerTest {

    @Test
    public void shouldSerializeToJson() {
        ClassWithDataFields objectToSerialize = new ClassWithDataFields();

        String myJson = new JsonSerializer().toJson(objectToSerialize);

        ClassWithDataFields objectFromJson = new Gson().fromJson(myJson, ClassWithDataFields.class);

        assertThat("Serialized object should contain all fields", objectToSerialize , samePropertyValuesAs(objectFromJson));
    }

    @Test
    public void shouldSerializeToJson2() {
        ClassWithObjectField objectToSerialize = new ClassWithObjectField();

        String myJson = new JsonSerializer().toJson(objectToSerialize);

        ClassWithObjectField objectFromJson = new Gson().fromJson(myJson, ClassWithObjectField.class);

        assertThat("Should serialize object with other class in field", objectToSerialize , samePropertyValuesAs(objectFromJson));

    }

    @Test
    public void shouldSerializeNullValue() {
        String targetObject = null;

        String myJson = new JsonSerializer().toJson(targetObject);
        String gson = new Gson().toJson(targetObject);

        Assert.assertEquals(gson, myJson);
    }

    @Test
    public void shouldSerializeStringValue() {
        String targetObject = "abc";

        String myJson = new JsonSerializer().toJson(targetObject);
        String gson = new Gson().toJson(targetObject);

        assertThat(gson, is(myJson));
    }

    @Test
    public void shouldSerializeIntValue() {
        int targetObject = 123;

        String myJson = new JsonSerializer().toJson(targetObject);
        String gson = new Gson().toJson(targetObject);

        assertThat(gson, is(myJson));
    }

    @Test
    public void shouldSerializeList() {
        List<String> targetObject = Arrays.asList("one", "two", "three");

        String myJson = new JsonSerializer().toJson(targetObject);
        String gson = new Gson().toJson(targetObject);

        assertThat(gson, is(myJson));
    }
}
