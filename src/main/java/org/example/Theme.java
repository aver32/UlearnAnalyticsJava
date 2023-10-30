package org.example;

public class Theme {
    private String nameTheme;
    private Task[] tasks;

    public Theme(String nameTheme, Task[] tasks) {
        this.nameTheme = nameTheme;
        this.tasks = tasks;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public Task[] getTasks() {
        return tasks;
    }
}
