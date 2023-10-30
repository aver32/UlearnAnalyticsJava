package org.example;

public class TaskResult {
    private Task currentTask;
    private double currentTaskResult;

    public TaskResult(Task currentTask, double currentTaskResult) {
        this.currentTask = currentTask;
        this.currentTaskResult = currentTaskResult;
    }

    public double getCurrentTaskResult() {
        return currentTaskResult;
    }

    public Task getCurrentTask() {
        return currentTask;
    }
}
