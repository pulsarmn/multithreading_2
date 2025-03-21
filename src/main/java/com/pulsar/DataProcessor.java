package com.pulsar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {

    private final AtomicInteger completedTasksCounter = new AtomicInteger(0);
    private final AtomicInteger activeTasks = new AtomicInteger(0);

    private final ExecutorService executorService;
    private final Map<String, Integer> taskResult;
    private final Object lock = new Object();

    public DataProcessor() {
        this(Executors.newCachedThreadPool());
    }

    public DataProcessor(ExecutorService executorService) {
        this.executorService = executorService;
        this.taskResult = new HashMap<>();
    }

    public void process(List<Integer> numbers) {
        String taskName = "task-" + completedTasksCounter.incrementAndGet();
        CalculateSumTask task = new CalculateSumTask(numbers, taskName);

        activeTasks.incrementAndGet();
        CompletableFuture.supplyAsync(task::call, executorService)
                .thenApply(sum -> {
                    synchronized (lock) {
                        taskResult.put(taskName, sum);
                    }
                    return sum;
                })
                .exceptionally(e -> {
                    System.out.println("Произошло исключение: " + e.getMessage());
                    completedTasksCounter.decrementAndGet();
                    return -1;
                })
                .thenRun(activeTasks::decrementAndGet);
    }

    public Optional<Integer> getResult(String taskName) {
        synchronized (lock) {
            return Optional.ofNullable(taskResult.get(taskName));
        }
    }

    public void shutdown() {
        executorService.shutdown();
        executorService.close();
    }

    public int getActiveCount() {
        return activeTasks.get();
    }

    public int getCompetedCount() {
        return completedTasksCounter.get();
    }
}
