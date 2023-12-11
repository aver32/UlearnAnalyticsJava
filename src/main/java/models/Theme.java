package models;

import java.util.ArrayList;

public class Theme {
    private String nameTheme;
    private ArrayList<Task> tasks;

    public Theme(String nameTheme) {

        tasks = new ArrayList<>();
        this.nameTheme = nameTheme;
    }
    public Theme(){

    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
