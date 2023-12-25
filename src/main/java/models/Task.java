package models;

import java.util.ArrayList;

public class Task {
    private final TaskType taskType;
    private final double maxPoint;
    private final ArrayList<TaskResult> taskResults;
    private final String nameTask;
    private final String themeName;
    private double currentPoints;

    public Task(TaskType taskType, String nameTask, double maxPoint, String themeName) {
        this.taskType = taskType;
        this.nameTask = nameTask;
        this.maxPoint = maxPoint;
        this.themeName = themeName;
        this.taskResults = new ArrayList<>();
    }

    public void addTaskResult(TaskResult taskResult) {
        taskResults.add(taskResult);
    }

    public TaskResult getTaskResultForStudent(String name) {
        for (TaskResult result: taskResults) {
            if (result.getCurrentStudent().name.equals(name)) {
                return result;
            }
        }
        return null;
    }

    public ArrayList<TaskResult> getTaskResults() {
        return taskResults;
    }

    public double getMaxPoint() {
        return maxPoint;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public double getCurrentPoints() {
        return currentPoints;
    }

    public String getNameTask() {
        return nameTask;
    }
    public String getThemeName() {
        return themeName;
    }

    public void setCurrentPoints(double currentPoints) {
        if (currentPoints < 0) {
            throw new IllegalArgumentException("Неверные баллы");
        }
        this.currentPoints = currentPoints;
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", taskType.getValue(), nameTask, maxPoint);
    }

    @Override
    public boolean equals(Object obj) {
        Task task = (Task) obj;
        return this.nameTask.equals(task.nameTask);
    }
}
