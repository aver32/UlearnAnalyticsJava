import db.DbOrmRepository;
import db.mapper.StudentFromDbMapper;
import db.models.TaskResultsEntity;
import models.Student;
import parser.CsvParser;
import visualization.drawer.BarChartDrawer;
import visualization.drawer.PieChartDrawer;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        var db = new DbOrmRepository();
        var studentsFromDb = new ArrayList<Student>();
        Map<String, Long> citiesAndCountMaxPoints = null;
        Map<String, Long> taskTypeAndCountMaxPoints = null;
        try {
            db.connect();
            var taskResultsFromDb = db.getTaskResults();
            citiesAndCountMaxPoints = findCityCountWithMaxPointsForEachTaskId(taskResultsFromDb);
            taskTypeAndCountMaxPoints = countStudentsWithMaxPointsByTaskType(taskResultsFromDb);
            studentsFromDb = db.getStudents()
                    .stream()
                    .map(StudentFromDbMapper::map)
                    .collect(Collectors.toCollection(ArrayList::new));
            db.close();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
        ArrayList<Student> finalStudentsFromDb = studentsFromDb;
        Map<String, Long> finalCitiesAndCountMaxPoints = citiesAndCountMaxPoints;
        Map<String, Long> finalTaskTypeAndCountMaxPoints = taskTypeAndCountMaxPoints;
        setUi(finalStudentsFromDb, finalCitiesAndCountMaxPoints, finalTaskTypeAndCountMaxPoints);
    }

    private static void setUi(ArrayList<Student> finalStudentsFromDb, Map<String, Long> finalCitiesAndCountMaxPoints,
                              Map<String, Long> finalTaskTypeAndCountMaxPoints) {
        SwingUtilities.invokeLater(() -> {

            JFrame mainFrame = new JFrame("Главное окно");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            JPanel pieChartPanel = PieChartDrawer.createStudentsByCitiesPanel(finalStudentsFromDb);
            tabbedPane.addTab("Студенты по городам", pieChartPanel);

            JPanel barChartPanelCities = BarChartDrawer.createMaxPointCountByCities(finalCitiesAndCountMaxPoints);
            tabbedPane.addTab("Количество максимальных баллов по городам", barChartPanelCities);

            JPanel barChartPanelTaskType = BarChartDrawer.createMaxPointCountByTaskType(finalTaskTypeAndCountMaxPoints);
            tabbedPane.addTab("Количество максимальных баллов по типам задания", barChartPanelTaskType);

            mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

    public static Map<String, Long> findCityCountWithMaxPointsForEachTaskId(List<TaskResultsEntity> taskResults) {
        Map<Long, Double> maxPointsByTaskId = taskResults.stream()
                .collect(Collectors.groupingBy(
                        TaskResultsEntity::getTaskId,
                        Collectors.mapping(TaskResultsEntity::getCurrentPoints, Collectors.maxBy(Double::compare))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().orElse(0.0)
                ));

        Map<String, Long> cityCountWithMaxPoints = taskResults.stream()
                .filter(result -> result.getCurrentPoints().equals(maxPointsByTaskId.get(result.getTaskId())))
                .filter(results -> results.getStudent().getCity() != null)
                .collect(Collectors.groupingBy(
                        result -> result.getStudent().getCity(),
                        Collectors.counting()
                ));

        cityCountWithMaxPoints.remove("");

        return cityCountWithMaxPoints;
    }

    public static Map<String, Long> countStudentsWithMaxPointsByTaskType(List<TaskResultsEntity> taskResults) {
        return taskResults.stream()
                .collect(Collectors.groupingBy(
                        result -> result.getTask().getTaskType(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(TaskResultsEntity::getCurrentPoints, Collectors.counting()),
                                countByPoints -> countByPoints.values().stream()
                                        .max(Long::compare)
                                        .orElse(0L)
                        )
                ));
    }


    private static void createDB(DbOrmRepository db, CsvParser parser) throws Exception {
        db.createThemesTable();
        db.createTasksTable();
        db.saveThemes(parser.getThemes());
        db.saveTasks(parser.getTasks());
        db.createStudentsTable();
        db.createTaskResultsTable();
        db.saveStudents(parser.getStudents());
        db.saveTaskResults(parser.getTasks());
    }
}