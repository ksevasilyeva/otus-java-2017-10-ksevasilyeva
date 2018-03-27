package ru.otus.java.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiThreadSortExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(MultiThreadSortExecutor.class);

    private int[] arrayTosSort;
    private int threadAmount;


    private MultiThreadSortExecutor(int threadAmount, int[] arrayTosSort) {
        this.threadAmount = threadAmount;
        this.arrayTosSort = arrayTosSort;
    }

    public static MultiThreadSortExecutor multiThreadSortExecutor(int threadAmount, int[] arrayTosSort) {
        return new MultiThreadSortExecutor(threadAmount, arrayTosSort);
    }

    public int[] sortArray() {
        LOG.info("Array to sort: {} in {} threads", arrayTosSort, threadAmount);
        int [][] arrayParts = splitArray();
        arrayParts = sortArrays(arrayParts);

        return mergeArrays(arrayParts);
    }

    private int[][] splitArray() {
        int lengthOfParts = arrayTosSort.length / threadAmount;
        int lengthOfLastPart = lengthOfParts + (arrayTosSort.length % threadAmount);

        int[][] arrayParts = new int[threadAmount][];

        LOG.info("Array split to {} pieces: ", threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            if (i != threadAmount - 1) {
                arrayParts[i] = new int[lengthOfParts];
                System.arraycopy(arrayTosSort, i * lengthOfParts, arrayParts[i], 0, lengthOfParts);
            } else {
                arrayParts[i] = new int[lengthOfLastPart];
                System.arraycopy(arrayTosSort, i * lengthOfParts, arrayParts[i], 0, lengthOfLastPart);
            }
            LOG.info(" {} ", arrayParts[i]);
        }

        return arrayParts;
    }

    private int [][] sortArrays(int[][] arrayParts) {
        List<Thread> threads = new ArrayList<>();
        for (int[] array : arrayParts) {
            final Thread thread = new Thread(() -> Arrays.sort(array));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return arrayParts;
    }

    private int[] mergeArrays(int[][] arrayParts) {
        int[] result = arrayParts[0];
        for (int i = 1; i < arrayParts.length; i++) {
            result = merge(result, arrayParts[i]);
        }
        LOG.info("Sorted and merged array: {}", result);
        return result;
    }

    private int[] merge(int[] leftPart, int[] rightPart) {
        int leftIndex = 0;
        int rightIndex = 0;
        int[] resultArray = new int[leftPart.length + rightPart.length];
        int i = 0;

        while (leftIndex < leftPart.length && rightIndex < rightPart.length) {
            if (leftPart[leftIndex] <= rightPart[rightIndex]) {
                resultArray[i] = leftPart[leftIndex];
                leftIndex++;
            } else {
                resultArray[i] = rightPart[rightIndex];
                rightIndex++;
            }
            i++;
        }

        if (leftIndex < leftPart.length) {
            for (int j=leftIndex; j<leftPart.length; j++) {
                resultArray[i] = leftPart[j];
                i++;
            }
        }

        if (rightIndex < rightPart.length) {
            for (int j=rightIndex; j<rightPart.length; j++) {
                resultArray[i] = rightPart[j];
                i++;
            }
        }
        return resultArray;
    }
}
