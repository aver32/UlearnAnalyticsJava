package org.example;

public class Student extends Human{
    private String group;
    private TaskResult[] taskResults;
    public Student(String name, String surname, String group) {
        super(name, surname);
    }

    public String getGroup() {
        return group;
    }

    public TaskResult[] getTaskResults() {
        return taskResults;
    }
}
