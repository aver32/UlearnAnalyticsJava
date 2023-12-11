package models;

public abstract class Human {
    protected String name;
    protected String surname;

    public Human(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getNameAndSurname() {
        if (!name.isEmpty()) {
            return name + " " + surname;
        } else {
            return surname;
        }
    }

    public String getSurname() {
        return surname;
    }
}
