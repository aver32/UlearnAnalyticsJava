package org.example;

public class Task {
    private TaskType taskType;
    private double maxPoint;
    private Theme currentTheme;
    private double currentPoints;

    public Task(TaskType taskType, double maxPoint, Theme currentTheme, double currentPoints) {
        this.taskType = taskType;
        this.maxPoint = maxPoint;
        this.currentTheme = currentTheme;
        this.currentPoints = currentPoints;
    }

    public double getMaxPoint() {
        return maxPoint;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public double getCurrentPoints() {
        return currentPoints;
    }
}
