package db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "students")
public class StudentEntity {
    public static final String NAME_COLUMN = "fullName";

    @DatabaseField(generatedId = true)
    private long studentId;

    @DatabaseField(canBeNull = false)
    private String fullName;

    @DatabaseField(canBeNull = false)
    private String academicGroup;

    @DatabaseField(canBeNull = true)
    private String city;

    public String getName() {
        if (fullName.split(" ", 1).length > 1) {
            return fullName.split(" ", 1)[0];
        }
        return fullName;
    }

    public String getSurname() {
        if (fullName.split(" ", 1).length > 1) {
            return fullName.split(" ", 1)[1];
        }
        return fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcademicGroup() {
        return academicGroup;
    }

    public String getCity() {
        return city;
    }

    public StudentEntity() {
    }

    public StudentEntity(String fullName, String academicGroup, String city) {
        this.fullName = fullName;
        this.academicGroup = academicGroup;
        this.city = city;

    }

    public long getStudentId() {
        return studentId;
    }
}
