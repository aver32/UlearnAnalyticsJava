package org.example;

public enum TaskType {
    ACTIVITY("Акт"),
    ADDITIONAL("Доп"),
    SEMINAR("Сем"),
    HOMEWORK("ДЗ"),
    EXERCISE("Упр");

    private final String value;

    TaskType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskType fromString(String value) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.value.equals(value)) {
                return taskType;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found");
    }
}
