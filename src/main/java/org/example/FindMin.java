package org.example;

public class FindMin {
    private final int firstIndex;
    private final int lastIndex;
    private final int[] array;
    private int minValue;
    private int minPosition;
    private final Controller controller;

    public FindMin(int firstIndex, int lastIndex, int[] array, Controller controller) {
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.array = array;
        this.controller = controller;
    }

    public void start() {
        minPosition = firstIndex;
        minValue = array[firstIndex];
        for (int i = firstIndex + 1; i < lastIndex; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minPosition = i;
            }
        }
        controller.finishedThread();
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMinPosition() {
        return minPosition;
    }
}
