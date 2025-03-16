package com.pulsar;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private final List<Integer> numbers;
    private final String taskName;

    public CalculateSumTask(List<Integer> numbers, String taskName) {
        this.numbers = numbers;
        this.taskName = taskName;
    }

    @Override
    public Integer call() {
        System.out.printf("Задача %s начала своё выполнение в потоке %s%n", taskName, Thread.currentThread().getName());

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.printf("Задача %s была прервана в потоке %s%n", taskName, Thread.currentThread().getName());
        }

        if (numbers == null) return 0;

        return numbers.stream()
                .mapToInt(value -> value)
                .sum();
    }
}
