package ru.otus.java.hw2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws InterruptedException {

        LOG.info("Empty String (with string pool) is: {}",
            ObjectSizeCalculator.sizeOf(() -> new String("")).calculate());

        LOG.info("Empty String (without string pool) is: {}",
            ObjectSizeCalculator.sizeOf(() -> new String(new char[0])).calculate());

        LOG.info("Empty Array is: {}",
            ObjectSizeCalculator.sizeOf(() -> new int[0]).calculate());

        LOG.info("Array with 1 element is: {}",
            ObjectSizeCalculator.sizeOf(() -> new int[1]).calculate());

        LOG.info("Array with 2 elements is: {}",
            ObjectSizeCalculator.sizeOf(() -> new int[2]).calculate());

        LOG.info("Array with 10 elements is: {}",
            ObjectSizeCalculator.sizeOf(() -> new int[10]).calculate());

        LOG.info("New Object() is: {}",
            ObjectSizeCalculator.sizeOf(Object::new).calculate());
    }
}
