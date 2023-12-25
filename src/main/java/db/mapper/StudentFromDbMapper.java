package db.mapper;

import db.models.StudentEntity;
import models.Student;

public class StudentFromDbMapper {
    public static Student map(StudentEntity student) {
        return new Student(student.getName(), student.getSurname(), student.getAcademicGroup(), student.getCity());
    }
}
