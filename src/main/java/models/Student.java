package models;

public class Student extends Human{
    private String group;
    private String city;
    public Student(String name, String surname, String group) {
        super(name, surname);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
    public void setCity(String cityName) {
        city = cityName;
    }

    @Override
    public String toString() {
        return String.format("%s %s город(%s)", surname, name, city);
    }
}