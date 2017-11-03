package ru.otus.java.hw3;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class MyArrayListTest {

    @Test
    public void shouldCreateEmptyArray() {
        MyArrayList<String> myArray = new MyArrayList<>();

        assertThat(myArray, is(empty()));
    }

    @Test
    public void shouldCreateArrayFromElements() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"AAA", "BBB"});

        assertThat(myArray, is(not(empty())));
        assertThat(myArray, hasItems("AAA", "BBB"));
        assertThat(myArray, hasSize(2));
    }

    @Test
    public void shouldAddElementToTheEnd() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"AAA", "BBB"});
        myArray.add("last");

        assertThat(myArray.get(myArray.size()-1), is("last"));
        assertThat(myArray.size(), is(3));
    }

    @Test
    public void shouldAddElementByIndex() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"AAA", "BBB"});
        myArray.add(0, "by index");

        assertThat(myArray.get(0), is("by index"));
        assertThat(myArray.size(), is(3));
    }

    @Test
    public void shouldAddCollectionToTheEnd() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"AAA", "BBB"});
        myArray.addAll(Arrays.asList("1", "2"));

        assertThat(myArray, hasItems("1", "2"));
        assertThat(myArray.indexOf("1"), is(2));
        assertThat(myArray.indexOf("2"), is(3));
    }

    @Test
    public void shouldAddCollectionByIndex() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"AAA", "BBB"});
        myArray.addAll(1, Arrays.asList("1", "2"));

        assertThat(myArray, hasItems("1", "2"));
        assertThat(myArray.indexOf("1"), is(1));
        assertThat(myArray.indexOf("2"), is(2));
        assertThat(myArray.indexOf("BBB"), is(3));
    }

    @Test
    public void shouldSupportSorting() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"1", "9", "5", "2"});
        Collections.sort(myArray);

        assertThat(myArray, contains("1", "2", "5", "9"));
    }

    @Test
    public void shouldSupportCopying() {
        MyArrayList<String> myArray = new MyArrayList<>(new String[]{"1", "9", "5", "2"});
        MyArrayList<String> targetArray = new MyArrayList<>(new String[]{"0", "0", "0", "0"});

        Collections.copy(targetArray, myArray);

        assertThat(targetArray, contains("1", "9", "5", "2"));
    }
}
