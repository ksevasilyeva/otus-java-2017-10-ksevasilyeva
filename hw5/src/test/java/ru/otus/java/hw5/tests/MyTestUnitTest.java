package ru.otus.java.hw5.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw5.myTestUnit.annotations.After;
import ru.otus.java.hw5.myTestUnit.annotations.Before;
import ru.otus.java.hw5.myTestUnit.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MyTestUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MyTestUnitTest.class);

    @Before
    public void setUp() {
        LOG.info("Setting up " + MyTestUnitTest.class.getName());
    }

    @Test
    public void shouldPass() {
        assertThat(true, is(true));

    }

    @Test
    public void shouldPass2() {
        assertThat(1, is(1));

    }

    @Test
    public void shouldFail() {
        assertThat(true, is(false));
    }

    @After
    public void tearDown() {
        LOG.info("After method for  " + MyTestUnitTest.class.getName());
    }

    @After
    public void secondTearDown() {
        LOG.info("Second After method for  " + MyTestUnitTest.class.getName());
    }
}
