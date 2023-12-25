package visualization.drawer;

import models.TaskResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleInsets;
import visualization.mapper.ChartDataMapper;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BarChartDrawer extends JFrame {
    public BarChartDrawer(String title, Map<String, Long> taskResults) {
        super(title);
        setContentPane(createMaxPointCountByCities(taskResults));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 300);
    }

    public static JPanel createMaxPointCountByCities(Map<String, Long> taskResults)
    {
        JFreeChart chart = createBarChartCities(ChartDataMapper.createCitiesAndCountMaxPoints(taskResults));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }

    public static JPanel createMaxPointCountByTaskType(Map<String, Long> maxPointCount) {
        JFreeChart chart = createBarChartTaskType(ChartDataMapper.createCitiesAndCountMaxPoints(maxPointCount));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }

    private static JFreeChart createBarChartCities(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Количество максимальных баллов по городам",
                "Города",
                "Количество максимальных баллов",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false
        );

        chart.addSubtitle(new TextTitle("Данные с вк"));
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    private static JFreeChart createBarChartTaskType(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Количество максимальных баллов по типам заданий",
                "Тип задания",
                "Количество максимальных баллов",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false
        );

        chart.addSubtitle(new TextTitle("Данные с csv"));
        chart.setBackgroundPaint(Color.white);

        return chart;
    }
}
