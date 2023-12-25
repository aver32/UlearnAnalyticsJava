package db;

import models.Student;
import models.Task;
import models.Theme;

import java.sql.*;
import java.util.*;

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
                 student_id integer PRIMARY KEY,
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

    public static void createTableTasks(){
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                 task_id INT PRIMARY KEY,
                 theme_id INT,
                 taskType text NOT NULL,
                 nameTask text NOT NULL,
                 maxPoint DOUBLE NOT NULL,
                 currentPoints DOUBLE,
                 FOREIGN KEY (theme_id) REFERENCES Theme(theme_id)
                );""";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Table tasks created");
        }
        catch (SQLException e){
            System.out.printf(e.getMessage());
        }
    }

    public static void createTableTaskResults(){
        String sql = """
                CREATE TABLE IF NOT EXISTS taskResults (
                 taskResult_id integer PRIMARY KEY,
                 task_id INT,
                 student_id INT,
                 currentTaskResult DOUBLE NOT NULL,
                 FOREIGN KEY (task_id) REFERENCES Task(task_id),
                 FOREIGN KEY (student_id) REFERENCES Student(student_id)
                );""";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Table taskResults created");
        }
        catch (SQLException e){
            System.out.printf(e.getMessage());
        }
    }

    public static void createTableThemes(){
        String sql = """
                CREATE TABLE IF NOT EXISTS themes (
                 theme_id integer PRIMARY KEY,
                 name text
                );""";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Table themes created");
        }
        catch (SQLException e){
            System.out.printf(e.getMessage());
        }
    }

    public static void saveTasksResults(Set<Theme> themes){
        String sql = "INSERT INTO taskResults(task_id, student_id, currentTaskResults) values(?, ?, ?)";

        for (var theme: themes){
            for (var task: theme.getTasks()){
                try (Connection connection = DriverManager.getConnection(URL);
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setString(1, getThemeId(theme.getNameTheme()));
                    preparedStatement.setString(2, task.getTaskType().toString());
                    preparedStatement.setString(3, task.getNameTask());
                    preparedStatement.setString(4, String.format("%s", task.getMaxPoint()));
                    preparedStatement.setString(5, String.format("%s", task.getCurrentPoints()));
                    preparedStatement.executeUpdate();
                }
                catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    public static void saveTasks(Set<Theme> themes){
        String sql = "INSERT INTO tasks(task_id ,theme_id, taskType, nameTask, maxPoint, currentPoints) values(?, ?, ?, ?, ?, ?)";
        int i = 1;
        for (var theme: themes){
            for (var task: theme.getTasks()){
                try (Connection connection = DriverManager.getConnection(URL);
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setString(1, String.format("%S", i));
                    preparedStatement.setString(2, getThemeId(theme.getNameTheme()));
                    preparedStatement.setString(3, task.getTaskType().toString());
                    preparedStatement.setString(4, task.getNameTask());
                    preparedStatement.setString(5, String.format("%s", task.getMaxPoint()));
                    preparedStatement.setString(6, String.format("%s", task.getCurrentPoints()));
                    preparedStatement.executeUpdate();
                    i+=1;
                }

                catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    public static void saveThemes(Set<Theme> themes){
        String sql = "INSERT INTO themes(name) values(?)";

        for (var theme: themes){
            try (Connection connection = DriverManager.getConnection(URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, theme.getNameTheme());
                preparedStatement.executeUpdate();
                System.out.printf(theme.getNameTheme() + " добавлен в таблицу themes\n");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
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

    public static String getThemeId(String themeName){
        String sql = String.format("SELECT theme_id FROM themes WHERE name='%s'", themeName);


        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            String result = resultSet.getString("theme_id");
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
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
