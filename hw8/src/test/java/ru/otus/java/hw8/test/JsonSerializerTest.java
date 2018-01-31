package ru.otus.java.hw8.test;

import com.google.gson.Gson;
import org.junit.Test;
import ru.otus.java.hw8.JsonSerializer;
import ru.otus.java.hw8.data.ClassWithDataFields;
import ru.otus.java.hw8.data.ClassWithObjectField;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

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
}
