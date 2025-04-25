package org.example;

import org.example.Controller;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int arrayLength = 10_000_000;

        long startTime = System.currentTimeMillis();
        int[] array = generateRandomPositiveArray(arrayLength);
        long endTime = System.currentTimeMillis();
        System.out.printf("Array creating time: %.2f ms%n", (endTime - startTime) / 1000.0);

        for (int threads = 64; threads <= 64; threads *= 2) {
            Controller controller = new Controller(array, threads);
            startTime = System.currentTimeMillis();
            controller.startController();
            endTime = System.currentTimeMillis();
            System.out.printf("Threads: %d  Time: %.2f ms%n", threads, (endTime - startTime) / 1000.0);
        }
    }

    public static int[] generateRandomPositiveArray(int length) {
        Random random = new Random();
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(Integer.MAX_VALUE - 1) + 1;
        }
        int index = random.nextInt(length - 2) + 1;
        System.out.println(index);
        array[index] = -1;
        return array;
    }
}