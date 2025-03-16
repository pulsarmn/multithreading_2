package com.pulsar;

import java.util.HashMap;
import java.util.Map;
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
