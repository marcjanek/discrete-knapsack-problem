package pl.edu.pw.elka.pszt.knapsack.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.edu.pw.elka.pszt.knapsack.algorithm.genetic.model.Population;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class Chart extends JFrame {
    private final List<Population> populations;

    public Chart(List<Population> populations) {
        this.populations = populations;
        initUI();
    }

    private void initUI() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        xySeriesCollection.addSeries(createSeries("average",
                populations.stream()
                        .map(e -> new Point(e.getNumber().intValue(),
                                (int) e.getAverageScore())).collect(Collectors.toList())
                )
        );
        xySeriesCollection.addSeries(createSeries("max",
                populations.stream()
                        .map(e -> new Point(e.getNumber().intValue(),
                                (int) e.getMaxScore())).collect(Collectors.toList())
                )
        );

        xySeriesCollection.addSeries(createSeries("min",
                populations.stream()
                        .map(e -> new Point(e.getNumber().intValue(),
                                (int) e.getMinScore())).collect(Collectors.toList())
                )
        );
        JFreeChart chart = createChart(xySeriesCollection);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Genetic populations");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYSeries createSeries(String name, List<Point> list) {
        XYSeries series = new XYSeries(name);
        list.forEach(point -> series.add(point.getX(), point.getY()));
        return series;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Genetic populations",
                "Population number",
                "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Genetic populations fitness per population number",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }
}
