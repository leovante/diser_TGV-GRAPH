package com;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HabrGraph extends JFrame {
    private ArrayList <Object> objectPointV1 = new ArrayList <Object>();
    private ArrayList <Object> objectPointV2 = new ArrayList <Object>();
    private ArrayList <Object> objectPipe = new ArrayList <Object>();
    private ArrayList <Integer> fromPoint = new ArrayList <Integer>(); //номер первой точки
    private ArrayList <Integer> toPoint = new ArrayList <Integer>(); // номер второй точки
    private ArrayList <Double> xcoord = new ArrayList <Double>();
    private ArrayList <Double> ycoord = new ArrayList <Double>();

    public static void mainTree() {
        System.out.println("Третий этап - запуск com.HabrGraph");
        HabrGraph frame = new HabrGraph();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setVisible(true);
    }

    public HabrGraph() {
        super("Temnikov prog. (v. Alpha)");
        String fileOne = "D:/Develop/GitHub/com.Kruskal/src/com/3 Оптимизированная матрица.txt/";
        String fileTwo = "D:/Develop/GitHub/com.Kruskal/src/com/2 Матрица всех точек.txt/";

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        try { //парсинг первой точки, второй точки и расстояния между ними
            FileReader first = new FileReader(fileOne);
            BufferedReader buff = new BufferedReader(first);
            String line = buff.readLine();
            while (line != null) {
                StringTokenizer tok = new StringTokenizer(line, " ");
                while (tok.hasMoreTokens()) {
                    int from = Integer.valueOf(tok.nextToken()).intValue();
                    int to = Integer.valueOf(tok.nextToken()).intValue();
                    int cost = (Integer.valueOf(tok.nextToken()).intValue()); // здесь расстояние между точками мне не нужно
                    fromPoint.add(from);
                    toPoint.add(to);
                }
                line = buff.readLine();
            }
            buff.close();
        } catch (IOException e) {
            //
        }

        try {//парсинг координат в массив индекс в массиве это номер точки
            FileReader fr = new FileReader(fileTwo);

            BufferedReader buffTwo = new BufferedReader(fr);
            String lineTwo = buffTwo.readLine();

            while (lineTwo != null) {
                StringTokenizer tokTwo = new StringTokenizer(lineTwo, " ");
                while (tokTwo.hasMoreTokens()) {
                    double x = Double.valueOf(tokTwo.nextToken()).intValue();
                    double y = Double.valueOf(tokTwo.nextToken()).intValue();
                    int from = Integer.valueOf(tokTwo.nextToken()).intValue();
                    int to = Integer.valueOf(tokTwo.nextToken()).intValue();
                    int cost = Integer.valueOf(tokTwo.nextToken()).intValue();
                    while (xcoord.size()<136){
                        xcoord.add(x*2);
                        ycoord.add(-y*2+600);
                        break;
                    }
                }
                lineTwo = buffTwo.readLine();
            }
            buffTwo.close();
            fr.close();
        } catch (IOException e) {
            //
        }

        try { //визуализация объектов первой точки, второй точки и отрезка между ними
            for (int i = 0; i < fromPoint.size(); i++) {
                // данные первой точки
                java.lang.Object v = graph.insertVertex(parent, null, fromPoint.get(i), xcoord.get(fromPoint.get(i)), ycoord.get(fromPoint.get(i)), 22, 15);
                java.lang.Object v2 = graph.insertVertex(parent, null, toPoint.get(i), xcoord.get(toPoint.get(i)), ycoord.get(toPoint.get(i)), 22, 15);
                objectPointV1.add(v);
                objectPointV2.add(v2);

                graph.insertEdge(parent, null, " ", v, v2);
            }
        } finally {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}