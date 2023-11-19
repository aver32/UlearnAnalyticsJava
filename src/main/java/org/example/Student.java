package org.example;

import java.util.ArrayList;

public class Student extends Human{
    private String group;
    public Student(String name, String surname, String group) {
        super(name, surname);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.format("%s %s", surname, name);
    }
}
