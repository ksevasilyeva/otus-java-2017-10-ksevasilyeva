package ru.otus.java.hw16.common.messages;

public abstract class Message {

    public static final String CLASS_NAME_VARIABLE = "className";

    private String className;

    String direction;

    private int backPortNumber;
    private int targetPortNumber;

    public <T> Message(Class<T> clazz, String direction, int backPortNumber, int targetPortNumber) {
        this.className = clazz.getName();
        this.direction = direction;
        this.backPortNumber = backPortNumber;
        this.targetPortNumber = targetPortNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getClassName() {
        return className;
    }

    public int getBackPortNumber() {
        return backPortNumber;
    }

    public void setBackPortNumber(int backPortNumber) {
        this.backPortNumber = backPortNumber;
    }

    public int getTargetPortNumber() {
        return targetPortNumber;
    }

    public void setTargetPortNumber(int targetPortNumber) {
        this.targetPortNumber = targetPortNumber;
    }
}
