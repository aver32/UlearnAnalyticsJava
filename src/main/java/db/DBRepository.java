package db;

import models.Student;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBRepository {
    private static Connection connection = null;
    private static final String URL = "jdbc:sqlite:C:\\Users\\flyli\\IdeaProjects\\UlearnAnalyticsJava\\db\\test.db";

    public static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createTableStudents(){
        String sql = """
                CREATE TABLE IF NOT EXISTS students (
                 id integer PRIMARY KEY,
                 full_name text NOT NULL,
                 academic_group text NOT NULL,
                 city text
                );""";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Table students created");
        }
        catch (SQLException e){
            System.out.printf(e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students){
        String sql = "INSERT INTO students(full_name, academic_group, city) values(?, ?, ?)";

        for (var student: students){
            try (Connection connection = DriverManager.getConnection(URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, student.getNameAndSurname());
                preparedStatement.setString(2, student.getGroup());
                preparedStatement.setString(3, student.getCity());
                preparedStatement.executeUpdate();
                System.out.printf(student.getNameAndSurname() + " добавлен в таблицу students\n");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static Map<String, String> getStudents(){
        String sql = "SELECT full_name, city FROM students";

        Map<String, String> studentsByCities = new HashMap<String, String>();

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){

            while (resultSet.next()){
                String fullName = resultSet.getString("full_name");
                String city = resultSet.getString("city");

                String[] nameAndSurname = fullName.split(" ");
                var surname = nameAndSurname[0].trim();
                var name = nameAndSurname.length > 1 ?
                        String.join(" ",
                                Arrays.stream(nameAndSurname).skip(1).toList()).trim() : "";
                studentsByCities.put(name + " " + surname, city);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return studentsByCities;
    }
}
