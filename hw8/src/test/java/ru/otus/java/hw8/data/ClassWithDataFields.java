package ru.otus.java.hw8.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassWithDataFields {

    public int countInt = 2;

    public int countInteger = 4;

    public boolean booleanTrue = true;

    public Boolean booleanFalse = false;

    public String name = "CLASS NAME";

    public String[] stringArray = {"H", "E", "L", "L", "0"};

    public int[] intArray = {2, 0, 1, 8};

    List<String> cities = Arrays.asList("Moscow", "Saint-P", "Berlin");

    Set<Integer> integerSet = new HashSet<>(Arrays.asList(11, 22, 33));

    public Integer nullValue = null;

    private Integer privateField = 0;
}
