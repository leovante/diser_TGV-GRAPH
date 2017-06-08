package com;

import java.io.*;
import java.util.*;

public class Parsing {
    private ArrayList <Double> xcoord; // X координата точек
    private ArrayList <Double> ycoord; //Y координата точек
    private ArrayList <Integer> num; // количество точек
    private int[][] matrix; // матрица, в которой между всеми точками рассчитывается расстояние
    private int number = 0;
    private String firstFile = "C:/Users/pro22/IdeaProjects/belgorodGraph/src/main/resources/1_fileToParsing.txt";
    private String textFromParser; // сюда записывается строка, которая содержит проПарсенные данные

    public void mainOne() throws IOException {
        System.out.println("Первый этап - запуск парсера");
        Parsing k = new Parsing();
        k.readInGraphXYN(this.firstFile);
        k.readInGraphLenght();
    }

    public Parsing() {
        xcoord = new ArrayList <Double>();
        ycoord = new ArrayList <Double>();
        num = new ArrayList <Integer>();
    }

    public void readInGraphXYN(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(file);
            String line = buff.readLine();
            while (line != null) {
                System.out.println("*Текст найден! Начинаю парсить файл");
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
                this.textFromParser = (xcoord.get(j) + " " + ycoord.get(j) + " " + i + " " + j + " " + this.matrix[i][j] + " ");
            }
        }
        System.out.print("*Парсинг закончен!");
    }

/*
    public void textValidate(){
        try {
            FileWriter fstream1 = new FileWriter(firstFile);// конструктор с одним параметром - для перезаписи
            BufferedWriter buff = new BufferedWriter(fstream1); //  создаём буферезированный поток
            String line = buff.readline();
            if(out1.)
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (Exception e)
        {System.err.println("Error in file cleaning: " + e.getMessage());}

        com.Parsing getText = new com.Parsing();
        String textValidate = getText.textFromParser;
        if(textValidate != null){
            System.out.println("Парсинг выполнен!");
        }else{
            System.out.println("Ошибка парсинга!");
        }
    }
*/
}