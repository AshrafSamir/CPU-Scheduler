import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

class pair {
    public String name;
    public Color color;

    pair(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}

public class Graph {
    private JPanel dataPanel;
    private JPanel panel;
    private JTable informationTable;
    private JTable processScheduleTable;
    private JLabel awtLabel;
    private JLabel atatLabel;
    private JFrame frame;

    private Map<Integer, Color> colorMapping = new HashMap<>();
    private Map<String, pair> process = new HashMap<>();
    private Vector<Process> processes = new Vector<>();
    private Vector<Process> terminatedProcesses = new Vector<>();

    private String ScheduleName;
    private int width = 500;
    private int height = 500;
    private int numberOfUniqueProcesses;
    private int biggestTime;
    private double awt = 0, atat = 0;
    private DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();


    private Color backgroundColor = Color.WHITE;


    public Graph(String Schedule) {
        this.ScheduleName = Schedule;
        frame = new JFrame();

        frame.setTitle(this.ScheduleName + " Schedule");
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                width = frame.getWidth() - 500;
                height = frame.getHeight();
                if (width > 1094)
                    width = 1094;
                else if (width < 258)
                    width = 258;
                if (height > 852)
                    height = 852;
                else if (height < 168)
                    height = 168;
            }
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        updateframe(defaultcategorydataset);

        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }
    private synchronized JFreeChart createChart(final CategoryDataset categorydataset) {
        JFreeChart jfreechart = ChartFactory.createLineChart("", "Process", "Time (Seconds)",
                categorydataset, PlotOrientation.HORIZONTAL, false, true, false);

        jfreechart.setBackgroundPaint(Color.white);

        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.lightGray);

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        numberaxis.setLowerBound(0);
        numberaxis.setUpperBound(biggestTime);

        for (int i = 0; i < biggestTime; ++i) {
            ValueMarker VM = new ValueMarker(i);
            VM.setPaint(Color.BLACK);
            categoryplot.addRangeMarker(VM);
        }

        categoryplot.setRenderer(this.createLineAndShapeRenderer(categorydataset));

        return jfreechart;
    }

    private synchronized LineAndShapeRenderer createLineAndShapeRenderer(final CategoryDataset categorydataset) {
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer() {
            @Override
            public Paint getItemPaint(int row, int col) {
                Paint cpaint = getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }

            @Override
            public Shape getItemShape(int row, int column) {
                int w = (int) (width * 0.866) / ((biggestTime == 0)? 1 : biggestTime);
                int h = (int) (height * 0.800) / ((numberOfUniqueProcesses == 0) ? 1 : numberOfUniqueProcesses);
                super.getItemShape(row, column);
                Rectangle r = new Rectangle(w, h);
                return r;
            }

            public Color getItemColor(int row, int col) {
                int y = categorydataset.getValue(row, col).intValue();
                return colorMapping.get(y);
            }
        };

        lineandshaperenderer.setItemMargin(0);
        lineandshaperenderer.setSeriesShapesVisible(0, true);
        lineandshaperenderer.setSeriesLinesVisible(0, false);

        return lineandshaperenderer;
    }

    private synchronized void buildInformationTable() {
        String[] header = {"Process", "Color", "Name"};
        DefaultTableModel tableData = new DefaultTableModel(header, 0);
        String[][] tableRow = new String[process.size()][3];

        int i = 0;
        for (Map.Entry<String, pair> p : process.entrySet()) {
            tableRow[i][0] = p.getKey();
            tableRow[i][2] = p.getValue().name;
            tableData.addRow(tableRow[i++]);
        }

        tableData.setRowCount(i);

        informationTable.setModel(tableData);
        informationTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table
                    , Object value
                    , boolean isSelected
                    , boolean hasFocus
                    , int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 1)
                    c.setBackground(process.get(table.getValueAt(row, 0)).color);
                else
                    c.setBackground(Color.white);
                return c;
            }
        });
    }

    private synchronized void buildProcessScheduleTable(Vector<Process> terminatedProcesses){
        String[] header = {"Process Name", "Arrival Time", "Waiting Time", "Turn Around Time"};
        DefaultTableModel tableData = new DefaultTableModel(header, 0);

        String[][] tableRow = new String[terminatedProcesses.size()][4];

        int i = 0;
        for (; i < terminatedProcesses.size() ; ++i) {
            tableRow[i][0] = terminatedProcesses.get(i).getName();
            tableRow[i][1] = terminatedProcesses.get(i).getArrivalTime() + ""; //add arrival Time
            tableRow[i][2] = terminatedProcesses.get(i).getWaitingTime() + ""; //add waiting Time.
            tableRow[i][3] = terminatedProcesses.get(i).getTurnaroundTime() + "";
            tableData.addRow(tableRow[i]);
        }

        //tableData.setRowCount(i);
        processScheduleTable.setModel(tableData);

    }

    private synchronized JPanel updatePanel(DefaultCategoryDataset defaultcategorydataset){//(Vector<Process> processes) {
        JFreeChart jfreechart = createChart(defaultcategorydataset);
        return new ChartPanel(jfreechart);
    }

    private synchronized void updateframe(DefaultCategoryDataset defaultcategorydataset) {
        this.width = 500;
        this.height = 500;

        JPanel graphPanel = updatePanel(defaultcategorydataset);
        graphPanel.setPreferredSize(new Dimension(width, height));
        graphPanel.setMaximumSize(new Dimension(600,600));

        dataPanel.setPreferredSize(new Dimension(width, height));
        dataPanel.setBackground(this.backgroundColor);

        if (frame.isVisible())
            panel.removeAll();

        panel.add(graphPanel, BorderLayout.CENTER);
        panel.add(dataPanel, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(700, 500));

        frame.add(panel);
        frame.pack();

        this.buildInformationTable();
        this.buildProcessScheduleTable(this.terminatedProcesses);

        if (!frame.isVisible())
            RefineryUtilities.centerFrameOnScreen(frame);

    }

    public synchronized void add(Process p, int time) throws InterruptedException {
        this.processes.add(p);

        defaultcategorydataset.addValue(time, String.valueOf(time), p.getName());
        colorMapping.putIfAbsent(time, p.getColor());
        process.putIfAbsent(p.getName(), new pair(p.getName(), p.getColor()));

        numberOfUniqueProcesses = process.size();
        biggestTime = (biggestTime > (time + 1))? biggestTime : time + 1;

        this.updateframe(defaultcategorydataset);
        Thread.sleep(200);
    }

    public synchronized void addTerminatedProcess(Process process){
        this.terminatedProcesses.add(process);
        this.buildProcessScheduleTable(this.terminatedProcesses);

        awt += process.getWaitingTime();
        atat += process.getTurnaroundTime();

        awtLabel.setText("AWT : " + awt/this.terminatedProcesses.size());
        atatLabel.setText("ATAT : " + atat/this.terminatedProcesses.size());

    }

    public static void main(String args[]) {
        Graph plot = new Graph("SJF");
    }
}