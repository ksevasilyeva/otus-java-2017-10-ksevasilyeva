package ru.otus.java.hw5.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw5.myTestUnit.core.TestStatus;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class TestResultLogger {

    private static final Logger LOG = LoggerFactory.getLogger(TestResultLogger.class);

    public static void logCaseResult(Method method, TestStatus status, Throwable err) {
        switch (status) {
            case PASSED:
                LOG.info(new StringBuilder("Ending test: [")
                    .append(method.getName())
                    .append("] Status: ")
                    .append(TestStatus.PASSED).toString());
                break;
            case BROKEN:
                LOG.error(new StringBuilder("Ending test: [")
                        .append(method.getName())
                        .append("] Status: ")
                        .append(TestStatus.BROKEN).toString(),
                    err);
                break;
            case FAILED:
                LOG.error(new StringBuilder("Ending test: [")
                        .append(method.getName())
                        .append("] Status: ")
                        .append(TestStatus.FAILED).toString(),
                    err);
        }
    }

    public static void logGeneralResult(Class clazz, List<TestStatus> status) {
        LOG.info(new StringBuilder("Test Execution for completed. ")
            .append(clazz.getName())
            .append(": ")
            .append(Collections.frequency(status, TestStatus.PASSED))
            .append(" PASSED, ")
            .append(Collections.frequency(status, TestStatus.FAILED))
            .append(" FAILED. \n").toString());
    }
}
