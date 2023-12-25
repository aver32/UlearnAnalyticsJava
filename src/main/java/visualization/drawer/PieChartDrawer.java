package visualization.drawer;

import models.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleInsets;
import visualization.mapper.ChartDataMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static org.jfree.chart.ChartFactory.createPieChart;

public class PieChartDrawer extends JFrame {
    public PieChartDrawer(String title, ArrayList<Student> studentsList) {
        super(title);
        setContentPane(createStudentsByCitiesPanel(studentsList));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public static JPanel createStudentsByCitiesPanel(ArrayList<Student> studentsList)
    {
        JFreeChart chart = createPieChart(ChartDataMapper.createStudentByCitiesDataset(studentsList));
        chart.setPadding(new RectangleInsets(4, 20, 2, 20));
        return new ChartPanel(chart);
    }

    private static JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "URFU STUDENTS BY CITIES",
                dataset,
                false,
                true,
                false
        );

        chart.setBackgroundPaint(
                new GradientPaint(
                        new Point(0, 0),
                        new Color(20, 20, 20),
                        new Point(400, 200),
                        Color.DARK_GRAY
                )
        );

        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));
        t.setText("URFU PROGRAMMING STUDENTS BY CITIES");

        return chart;
    }
}
