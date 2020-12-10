package Simulation;

import GUI.SimulationGUI;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
//import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
//import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
//import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.data.general.DatasetUtilities;
//import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class State {
    private int len = 1000;
    private int[][] map;
    private Node[][] nodeMap;
    private List<Person> peopleList;
    private double rvalue = 1.2;
    private double decreasingIndex = 0.998;

    private int[] dx = {0, 0, 1, -1};
    private int[] dy = {1, -1, 0, 0};

    private List<Integer> xList = new ArrayList<>();
    private List<Integer> yList = new ArrayList<>();

    public double peopleDensity;
    public boolean hasMaskingPolicy;
    public boolean hasTestingPolicy;
    public boolean hasQuarantinePolicy;
    public int cityNum;

    public State(double peopleDensity, boolean hasMaskingPolicy, boolean hasTestingPolicy,
                 boolean hasQuarantinePolicy, int cityNum, Node[][] nodeMap) {
        this.peopleDensity = peopleDensity;
        this.hasMaskingPolicy = hasMaskingPolicy;
        this.hasTestingPolicy = hasTestingPolicy;
        this.hasQuarantinePolicy = hasQuarantinePolicy;
        this.cityNum = cityNum;
        this.nodeMap = nodeMap;

    }

    public Node[][] getNodeMap() {
        return nodeMap;
    }


    public List<Integer> getxList() {
        return xList;
    }

    public List<Integer> getyList() {
        return yList;
    }

    public static Node[][] generateNodemap(int len, double peopleDensity){
        Node[][] result = new Node[len][len];
        int people = (int) (len * len * 0.01 * peopleDensity);
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                result[i][j] = new Node(i, j, false);
            }
        }

        for (int i = 0; i < people; i++) {
            Random rand = new Random();
            int x = rand.nextInt(len);
            int y = rand.nextInt(len);

            while (result[x][y].flag) {
                x = rand.nextInt(len);
                y = rand.nextInt(len);
            }

            Person p = new Person(x, y);
            if (i % (people * 0.05) == 0) {
                p.isPositive = true;
                p.positiveRemainDays = 30;
            }

            result[x][y].flag = true;
            result[x][y].person = p;
        }

        return result;
    }

    public int getPositiveCount() {
        int count = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (nodeMap[i][j].flag && nodeMap[i][j].person.isPositive) {
                    count++;
                }
            }
        }

        return count;
    }

    public void simulate() {
        int days = 500;
        Random rand = new Random();
        for (int k = 0; k < days; k++) {
            System.out.println( "Simulation in progress: " + k + " Day, total days: " + days);
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (!nodeMap[i][j].flag) {
                        continue;
                    }

                    Person crt = nodeMap[i][j].person;

                    if (hasQuarantinePolicy) {
                        int movePoss = rand.nextInt(10);
                        if (movePoss >= 8) {
                            if (crt.isPositive) {
                                crt.positiveRemainDays--;
                                if (crt.positiveRemainDays == 0) {
                                    crt.isPositive = false;
                                    crt.isHealed = true;
                                }
                            }
                            continue;
                        }
                    }

                    for (int w = 0; w < 4; w++) {
                        int new_x = i + dx[w];
                        int new_y = j + dy[w];

                        if (new_x < 0 || new_x >= len || new_y < 0 || new_y >= len
                                || nodeMap[new_x][new_y].flag) {
                            continue;
                        }

                        nodeMap[i][j].flag = false;
                        nodeMap[i][j].person = null;
                        crt.x = new_x;
                        crt.y = new_y;
                        nodeMap[new_x][new_y].flag = true;
                        nodeMap[new_x][new_y].person = crt;
                        break;
                    }

                    if (crt.isPositive) {
                        crt.positiveRemainDays--;
                        if (crt.positiveRemainDays == 0) {
                            crt.isPositive = false;
                            crt.isHealed = true;
                        }
                    } else {
                        for (int w = 0; w < 4; w++) {
                            int new_x = crt.x + dx[w];
                            int new_y = crt.y + dy[w];

                            if (new_x < 0 || new_x >= len || new_y < 0 || new_y >= len
                                    || !nodeMap[new_x][new_y].flag) {
                                continue;
                            }

                            if (nodeMap[new_x][new_y].person.isPositive) {
                                double decreasingVal = Math.pow(decreasingIndex, k + 1);
                                int val1 = (int) (rand.nextInt(10) * rvalue * decreasingVal);
                                int val2 = hasMaskingPolicy ? rand.nextInt(12) : rand.nextInt(14);
                                //int val2 = rand.nextInt(12);
                                int val3 = crt.isHealed ? rand.nextInt(10) : 10;
                                crt.isPositive = val1 >= 5 && val2 >= 7 && val3 >= 8 || crt.isPositive;
                            }
                        }

                        if (crt.isPositive) {
                            crt.positiveRemainDays = 30;
                            break;
                        }

                    }
                }
            }

            int postiveNum = getPositiveCount();
            xList.add(k);
            yList.add(postiveNum);
        }
    }

    public static void testSimulation(Boolean hasMaskingPolicy1, Boolean hasTestingPolicy1, Boolean hasQuarantinePolicy1, Boolean hasMaskingPolicy2, Boolean hasTestingPolicy2, Boolean hasQuarantinePolicy2){
        Node[][] map = generateNodemap(1000, 4.0);
        int n = map.length;
        Node[][] map2 = new Node[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                Node crt = map[i][j];
                int new_x = crt.x;
                int new_y = crt.y;
                boolean new_flag = crt.flag;
                Person new_person = new Person(new_x, new_y);
                if(crt.person != null){
                    new_person.isHealed = crt.person.isHealed;
                    new_person.isPositive = crt.person.isPositive;
                    new_person.positiveRemainDays = crt.person.positiveRemainDays;
                }

                Node newNode = new Node(crt.x, crt.y, new_flag);
                newNode.person = new_person;
                map2[i][j] = newNode;
            }
        }

        State testState1 = new State(4.0, hasMaskingPolicy1,  hasTestingPolicy1,
                hasQuarantinePolicy1, 10, map);
        testState1.simulate();

        State testState2 = new State(4.0, hasMaskingPolicy2,  hasTestingPolicy2,
                hasQuarantinePolicy2, 10, map2);
        testState2.simulate();
        generateGraph(testState1.xList, testState1.yList, testState2.xList, testState2.yList);
    }

    public static void generateGraph(List<Integer> xList, List<Integer> yList, List<Integer> xList2, List<Integer> yList2) {
        XYSeriesCollection mCollection = GetCollection(xList, yList, xList2, yList2);
        JFreeChart freeChart = ChartFactory.createXYLineChart(
                "Covid-19 Simulation Results",
                "Days",
                "Cases",
                mCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        ChartFrame mChartFrame = new ChartFrame("Covid-19 Simulation Results", freeChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);

    }

    public static XYSeriesCollection GetCollection(List<Integer> xList, List<Integer> yList, List<Integer> xList2, List<Integer> yList2)
    {
        XYSeriesCollection mCollection = new XYSeriesCollection();
//        XYSeries mSeriesFirst = new XYSeries("With quarantine and masking policy (Simulation1)");
//        XYSeries mSeriesSecond = new XYSeries("Without quarantine and masking policy (Simulation2)");
        XYSeries mSeriesFirst = new XYSeries("Simulation1");
        XYSeries mSeriesSecond = new XYSeries("Simulation2");


        for(int i = 0; i < xList.size(); i++){
            mSeriesFirst.add(Double.valueOf(xList.get(i)), Double.valueOf(yList.get(i)));
        }

        for(int i = 0; i < xList.size(); i++){
            mSeriesSecond.add(Double.valueOf(xList2.get(i)), Double.valueOf(yList2.get(i)));
        }

        mCollection.addSeries(mSeriesFirst);
        mCollection.addSeries(mSeriesSecond);

        return mCollection;
    }
}
