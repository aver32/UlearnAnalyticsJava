package models;

public class Student extends Human{
    private String group;
    private String city;
    public Student(String name, String surname, String group) {
        super(name, surname);
        this.group = group;
    }

    public Student(String name, String surname, String group, String city) {
        super(name, surname);
        this.group = group;
        this.city = city;
    }

    public String getGroup() {
        return group;
    }
    public String getCity() {return city;}
    public void setCity(String cityName) {
        city = cityName;
    }

    @Override
    public String toString() {
        return String.format("%s %s город(%s)", surname, name, city);
    }
}
