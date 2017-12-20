package ru.otus.java.hw5.myTestUnit.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw5.myTestUnit.annotations.After;
import ru.otus.java.hw5.myTestUnit.annotations.Before;
import ru.otus.java.hw5.myTestUnit.annotations.Test;
import ru.otus.java.hw5.utils.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static ru.otus.java.hw5.utils.TestResultLogger.logCaseResult;
import static ru.otus.java.hw5.utils.TestResultLogger.logGeneralResult;

public class MyTestUnitCore {

    private static final Logger LOG = LoggerFactory.getLogger(MyTestUnitCore.class);

    public static void runClasses(Class clazz) {
        List<TestStatus> generalTestClassResults = new ArrayList<>();

        List<Method> beforeMethods = ReflectionHelper.getMethodsByAnnotation(clazz, Before.class);
        List<Method> afterMethods = ReflectionHelper.getMethodsByAnnotation(clazz, After.class);
        List<Method> testMethods = ReflectionHelper.getMethodsByAnnotation(clazz, Test.class);

        LOG.info("Start executing Test Class: [" + clazz.getName() + "]");
        for (Method testMethod : testMethods) {
            try {
                LOG.info(">>> Start executing case: [" + testMethod.getName() + "] " );
                Object testClass = ReflectionHelper.instantiate(clazz);
                runMethods(testClass, beforeMethods);
                runMethod(testClass, testMethod);
                runMethods(testClass, afterMethods);
                logCaseResult(testMethod, TestStatus.PASSED, null);
                generalTestClassResults.add(TestStatus.PASSED);
            } catch (InvocationTargetException err) {
                logCaseResult(testMethod, TestStatus.FAILED, err);
                generalTestClassResults.add(TestStatus.FAILED);
            } catch (IllegalAccessException e) {
                logCaseResult(testMethod, TestStatus.BROKEN, e);
                generalTestClassResults.add(TestStatus.BROKEN);
            }
        }

        logGeneralResult(clazz, generalTestClassResults);
    }

    private static void runMethod(Object testClassInstance, Method method, Object... methodParameters)
                                                            throws InvocationTargetException, IllegalAccessException {
        if (isNull(method) || isNull(testClassInstance)) {
            return;
        }
        method.invoke(testClassInstance, methodParameters);
    }

    private static void runMethods(Object testClassInstance, List<Method> methods)
        throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            runMethod(testClassInstance, method, null);
        }
    }

    public static void runClassesFromPackage(String packageRef) {
        Set<Class<?>> testClasses = ReflectionHelper.getTestClassesByPackageReference(packageRef);
        if (testClasses.isEmpty()) {
            LOG.info("No class files found in package: " + packageRef);
        }

        testClasses.forEach(clazz -> runClasses(clazz));
    }
}
