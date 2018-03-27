package ru.otus.java.hw14;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static ru.otus.java.hw14.MultiThreadSortExecutor.multiThreadSortExecutor;

public class MultiThreadSortExecutorTest {

    @Test
    public void shouldSortInOddThreads() {
        int[] arrayToSort = new int[] {1, 5, 10, 12, 11, 34, 45, 0, 56, 33};
        int[] expectedArray = new int[] {0, 1, 5, 10, 11, 12, 33, 34, 45, 56};
        int[] sorted = multiThreadSortExecutor(3, arrayToSort)
            .sortArray();
        assertThat(sorted, equalTo(expectedArray));
    }

    @Test
    public void shouldSortInEvenThread() {
        int[] arrayToSort = new int[] {1, 5, 10, 12, 11, 34, 45, 0, 56, 33};
        int[] expectedArray = new int[] {0, 1, 5, 10, 11, 12, 33, 34, 45, 56};
        int[] sorted = multiThreadSortExecutor(4, arrayToSort)
            .sortArray();
        assertThat(sorted, equalTo(expectedArray));
    }
}
