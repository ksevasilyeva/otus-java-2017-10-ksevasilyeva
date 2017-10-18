package ru.otus.java.hw1;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map items = ImmutableMap.of(Math.random(), "val1", Math.random(), "val2", Math.random(), "val3");

        items.entrySet()
            .stream()
            .forEach(System.out::println);
    }
}
