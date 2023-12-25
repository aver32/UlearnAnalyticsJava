package parser;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import models.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvParser {
    private final CSVParser parser;
    private final String csvFilePath;
    private final Set<Theme> themes;
    private ArrayList<Task> tasks;
    private ArrayList<TaskResult> results;
    private ArrayList<Student> students;

    public CsvParser(String absoluteFilePath) {
        themes = new LinkedHashSet<>();
        tasks = new ArrayList<>();
        results = new ArrayList<>();
        students = new ArrayList<>();
        parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        csvFilePath = absoluteFilePath;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void parse() {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath, StandardCharsets.UTF_8)).withCSVParser(parser).build()) {
            String[] headerTheme = reader.readNext();
            String[] exercise = reader.readNext();
            String[] maxPointsTasks = reader.readNext();

            createTasksAndThemesLists(maxPointsTasks, exercise, headerTheme);
            createTasksResults(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Theme> getThemes() {
        return themes;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }

    private void createTasksResults(CSVReader reader) throws IOException, CsvException {
        for (String[] studentData: reader.readAll()) {
            String[] nameAndSurname = studentData[0].split(" ");
            var surname = nameAndSurname[0].trim();
            var name = nameAndSurname.length > 1 ?
                    String.join(" ",
                            Arrays.stream(nameAndSurname).skip(1).toList()).trim() : "";
            Student student = new Student(name, surname, studentData[1]);
            students.add(student);
            studentData = ArrayUtils.removeAll(studentData, 0, 1);
            for (int i = 0; i < studentData.length; i++) {
                TaskResult taskResult = new TaskResult(
                        Double.parseDouble(studentData[i]),
                        student);
                tasks.get(i).addTaskResult(taskResult);
            }
        }
    }

    private void createTasksAndThemesLists(String[] maxPointsTasks, String[] exercise, String[] headerTheme) {
        String curThemeName = "";
        Theme theme = new Theme();
        for (var i = 0; i < maxPointsTasks.length; i++) {
            if (!maxPointsTasks[i].matches("\\d+")) {
                continue;
            }
            var exerciseTypeAndName = exercise[i].split(": ");
            var exerciseName = exerciseTypeAndName.length == 2 ? exerciseTypeAndName[1] : "Нету имени";
            TaskType taskType = TaskType.fromString(exercise[i].split(":")[0]);

            curThemeName = taskType == TaskType.ACTIVITY ? headerTheme[i] : curThemeName;
            theme = taskType == TaskType.ACTIVITY ? new Theme(curThemeName) : theme;
            Task task = new Task(taskType, exerciseName, Double.parseDouble(maxPointsTasks[i]), theme.getNameTheme());
            theme.addTask(task);
            tasks.add(task);
            themes.add(theme);
        }
    }
}


