package org.example;

public class Controller {
    private final int[] array;
    private final FindMin[] findMinThreads;
    private final int findMinThreadNum;
    private int finishedThreads = 0;
    private final Object lockObject = new Object();

    public Controller(int[] array, int findMinThreadNum) {
        this.array = array;
        this.findMinThreadNum = findMinThreadNum;
        this.findMinThreads = new FindMin[findMinThreadNum];
    }

    public void startController() {
        int[][] ranges = splitRanges();
        Thread[] threads = new Thread[findMinThreadNum];

        for (int i = 0; i < findMinThreadNum; i++) {
            FindMin findMinThread = new FindMin(ranges[i][0], ranges[i][1], array, this);
            findMinThreads[i] = findMinThread;
            threads[i] = new Thread(findMinThread::start);
            threads[i].start();
        }

        synchronized (lockObject) {
            while (finishedThreads < findMinThreadNum) {
                try {
                    lockObject.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        int globalMin = findMinThreads[0].getMinValue();
        int globalMinPosition = findMinThreads[0].getMinPosition();
        for (int i = 1; i < findMinThreadNum; i++) {
            if (findMinThreads[i].getMinValue() < globalMin) {
                globalMin = findMinThreads[i].getMinValue();
                globalMinPosition = findMinThreads[i].getMinPosition();
            }
        }
        System.out.printf("Min: %d      Index: %d%n", globalMin, globalMinPosition);
    }

    public int[][] splitRanges() {
        int[][] ranges = new int[findMinThreadNum][2];
        int baseSize = array.length / findMinThreadNum;
        int remainder = array.length % findMinThreadNum;
        int current = 0;

        for (int i = 0; i < findMinThreadNum; i++) {
            int size = baseSize + (i < remainder ? 1 : 0);
            int end = current + size;
            ranges[i][0] = current;
            ranges[i][1] = end;
            current = end;
        }
        return ranges;
    }

    public void finishedThread() {
        synchronized (lockObject) {
            finishedThreads++;
            if (finishedThreads == findMinThreadNum) {
                lockObject.notifyAll();
            }
        }
    }
}
