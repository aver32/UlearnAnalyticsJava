package visualization.mapper;

import models.Student;
import models.TaskResult;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

public class ChartDataMapper {
    public static PieDataset createStudentByCitiesDataset(List<Student> students) {

        var studentsCountByCities = students.stream()
                .filter(student -> student.getCity() != null)
                .collect(
                        Collectors.groupingBy(
                                Student::getCity,
                                HashMap::new,
                                Collectors.counting()
                        )
                );

        DefaultPieDataset dataset = new DefaultPieDataset();

        studentsCountByCities.forEach(dataset::setValue);

        return dataset;
    }

    public static CategoryDataset createCitiesAndCountMaxPoints(Map<String, Long> citiesAndCountMaxPoints) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        citiesAndCountMaxPoints
                .forEach((city, count) -> dataset.setValue(count, "city", city));
        return dataset;
    }

    public static CategoryDataset createTaskTypeAndCountMaxPoints(Map<String, Long> taskTypeAndCountMaxPoints) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        taskTypeAndCountMaxPoints.forEach((city, count) -> dataset.setValue(count, "taskType", city));
        return dataset;
    }
}
