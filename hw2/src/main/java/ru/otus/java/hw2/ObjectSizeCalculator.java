package ru.otus.java.hw2;

import java.util.function.Supplier;

public class ObjectSizeCalculator {

    private static final int COUNT = 20_000_000;

    private final Supplier targetObject;

    public static ObjectSizeCalculator sizeOf(Supplier testObject) {
        return new ObjectSizeCalculator(testObject);
    }

    private ObjectSizeCalculator(Supplier testObject) {
        this.targetObject = testObject;
    }

    public long calculate() {

        callGC();

        Object[] targetObjectsArray = new Object[COUNT];

        long memoryBeforeObjectCreation = currentMemoryInUse();

        for (int i = 0; i < COUNT; i++) {
            targetObjectsArray[i] = targetObject.get();
        }

        long memoryAfterObjectCreation = currentMemoryInUse();

        callGC();

        return (memoryAfterObjectCreation - memoryBeforeObjectCreation) / COUNT;
    }

    private void callGC() {
        try {
            Runtime.getRuntime().gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private long currentMemoryInUse() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
