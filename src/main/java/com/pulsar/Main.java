package com.pulsar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor(Executors.newFixedThreadPool(10));

        for (int i = 0; i < 100; i++) {
            processor.process(getRandomList());
        }

        while (processor.getActiveCount() != 0) {}

        System.out.println("Completed: " + processor.getCompetedCount());
        for (int i = 1; i < 101; i++) {
            System.out.println(processor.getResult("task-" + i));
        }
        processor.shutdown();
    }

    private static List<Integer> getRandomList() {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < random.nextInt(0, 100); i++) {
            result.add(random.nextInt(1, 500));
        }

        return result;
    }
}
