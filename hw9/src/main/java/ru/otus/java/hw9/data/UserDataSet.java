package ru.otus.java.hw9.data;


public class UserDataSet extends DataSet {

    private String name;

    private int age;

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append(id).append(": ")
            .append(name).append(", ")
            .append(age)
            .toString();
    }
}
