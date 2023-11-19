package models;

public class TaskResult {
    private double currentTaskResult;
    private final Student currentStudent;

    public TaskResult(double currentTaskResult, Student student) {
        this.currentTaskResult = currentTaskResult;
        this.currentStudent = student;
    }

    public double getCurrentTaskResult() {
        return currentTaskResult;
    }

    @Override
    public String toString() {
        return String.format("Студент: %s - баллы: %s\n", currentStudent.toString(), currentTaskResult);
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }
}
