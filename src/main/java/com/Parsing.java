package com;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Parsing {
    private ArrayList<Double> xcoord; // X координата точек
    private ArrayList<Double> ycoord; //Y координата точек
    private ArrayList<Integer> num; // количество точек
    private int[][] matrix; // матрица, в которой между всеми точками рассчитывается расстояние
    private int number = 0;
    InputStream input = getClass().getResourceAsStream("1_parsFromFile.txt");
    private String parsFromFile = "1_parsFromFile.txt";
    private String parsToFile = "D:/Develop/GitHub/belgorodGraph/src/main/resources/2_toKruskal.txt";
    private String textToFile = ""; // сюда записывается строка, которая содержит проПарсенные данные

    public void mainOne() throws IOException, InterruptedException {
        System.out.println("Первый этап - запуск парсера");
        TimeUnit.SECONDS.sleep(2);
        Parsing p = new Parsing();
        p.readInGraphXYN(this.parsFromFile);
        p.readInGraphLenght();
        p.writeInFile();
    }

    public Parsing() {
        xcoord = new ArrayList<Double>();
        ycoord = new ArrayList<Double>();
        num = new ArrayList<Integer>();
    }

    public void readInGraphXYN(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(file);
            String line = buff.readLine();
            while (line != null) {
                System.out.println("*Текст найден! Начинаю парсить файл!");
                StringTokenizer tok = new StringTokenizer(line, " ");
                while (tok.hasMoreTokens()) {
                    int foo = Integer.valueOf(tok.nextToken()).intValue();
                    double xcoord = Double.valueOf(tok.nextToken()).doubleValue();
                    double ycoord = Double.valueOf(tok.nextToken()).doubleValue();
                    double z = Double.valueOf(tok.nextToken()).intValue();
                    this.xcoord.add(xcoord);
                    this.ycoord.add(ycoord);
                    this.num.add(this.number++);
                }
                line = buff.readLine();
            }
            buff.close();
            file.close();
        } catch (IOException e) {
            //
        }
    }

    public void readInGraphLenght() throws IOException {
        double xi;
        double yi;
        double xj;
        double yj;
        double xreverce;
        double yreverce;
        int i = 0;
        int j = 0;
        matrix = new int[num.size()][num.size()];

        for (i = 0; i < num.size(); i++) {
            for (j = 0; j < num.size(); j++) {
                xi = xcoord.get(i);
                yi = ycoord.get(i);
                xj = xcoord.get(j);
                yj = ycoord.get(j);
                if (xj < xi) {
                    xreverce = xi;
                    xi = xj;
                    xj = xreverce;
                }
                if (yj < yi) {
                    yreverce = yi;
                    yi = yj;
                    yj = yreverce;
                }
                int realize = (int) Math.sqrt(Math.pow(xj - xi, 2) + Math.pow(yj - yi, 2));
                this.matrix[i][j] = realize;
                this.textToFile = this.textToFile + (xcoord.get(j) + " " + ycoord.get(j) + " " + i + " " + j + " " + this.matrix[i][j] + " ");
            }
        }
        System.out.println("*Парсинг закончен!");
    }

    public void writeInFile() {
        System.out.println("*Записываю в файл!");
        try {
            FileWriter write = new FileWriter(this.parsToFile, false);
            write.write(this.textToFile);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}