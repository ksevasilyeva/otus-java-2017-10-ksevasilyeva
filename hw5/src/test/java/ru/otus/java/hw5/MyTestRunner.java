package ru.otus.java.hw5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw5.myTestUnit.core.MyTestUnitCore;
import ru.otus.java.hw5.tests.MyTestUnitTest;

public class MyTestRunner {
    private static final Logger LOG = LoggerFactory.getLogger(MyTestRunner.class);

    public static void main(String[] args) {
        LOG.info("Run tests by TestClass");
        MyTestUnitCore.runClasses(MyTestUnitTest.class);

        LOG.info("Run tests by Package reference");
        MyTestUnitCore.runClassesFromPackage("ru.otus.java.hw5.tests");
    }
}
