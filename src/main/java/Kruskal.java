package com;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class Kruskal {
    private final int MAX_NODES = 136;
    private HashSet nodes[];               // Array of connected components
    private TreeSet allEdges;              // Priority queue of Edge objects
    private Vector allNewEdges;            // Edges in Minimal-Spanning Tree

    public static void mainTwo() throws IOException {
        System.out.println("Второй этап - запуск алгоритма Краскала");
        Kruskal k = new Kruskal();
        String fileName = "D:/Develop/GitHub/Kruskal/src/com/2 Матрица всех точек и расстояний между ними.txt/";
        k.readInGraphData(fileName); //было args[0]
        k.performKruskal();
        k.printFinalEdges();
    }

    Kruskal() {
        // Constructor
        nodes = new HashSet[MAX_NODES];      // Create array for components
        allEdges = new TreeSet(new Edge());  // Create empty priority queue
        allNewEdges = new Vector(MAX_NODES); // Create vector for MST edges
    }

    private void readInGraphData(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(file);
            String line = buff.readLine();
            while (line != null) {
                StringTokenizer tok = new StringTokenizer(line, " ");
                while (tok.hasMoreTokens()) {
                    double xi = Double.valueOf(tok.nextToken()).intValue();
                    double yi = Double.valueOf(tok.nextToken()).intValue();
                    int from = Integer.valueOf(tok.nextToken()).intValue();
                    int to = Integer.valueOf(tok.nextToken()).intValue();
                    int cost = Integer.valueOf(tok.nextToken()).intValue();
//                    System.out.println(from + " " + to + " " + cost);
                    allEdges.add(new Edge(from, to, cost));  // Update priority queue
//                    System.out.println("Количество ребер: " + allEdges.size());
                    if (nodes[from] == null) {
                        // Create set of connect components [singleton] for this node
                        nodes[from] = new HashSet(2 * MAX_NODES);
                        nodes[from].add(new Integer(from));
                    }
                    if (nodes[to] == null) {
                        // Create set of connect components [singleton] for this node
                        nodes[to] = new HashSet(2 * MAX_NODES);
                        nodes[to].add(new Integer(to));
                    }
                }
                line = buff.readLine();
            }

            buff.close();
        } catch (IOException e) {
            //
        }
    }

    private void performKruskal() {
        int size = allEdges.size();
        for (int i = 0; i < size; i++) {
            Edge curEdge = (Edge) allEdges.first();
            if (allEdges.remove(curEdge)) {
                // successful removal from priority queue: allEdges
                if (nodesAreInDifferentSets(curEdge.from, curEdge.to)) {
                    // System.out.println("Nodes are in different sets ...");
                    HashSet src, dst;
                    int dstHashSetIndex;
                    if (nodes[curEdge.from].size() > nodes[curEdge.to].size()) {
                        // have to transfer all nodes including curEdge.to
                        src = nodes[curEdge.to];
                        dst = nodes[dstHashSetIndex = curEdge.from];
                    } else {
                        // have to transfer all nodes including curEdge.from
                        src = nodes[curEdge.from];
                        dst = nodes[dstHashSetIndex = curEdge.to];
                    }
                    Object srcArray[] = src.toArray();
                    int transferSize = srcArray.length;
                    for (int j = 0; j < transferSize; j++) {
                        // move each node from set: src into set: dst
                        // and update appropriate index in array: nodes
                        if (src.remove(srcArray[j])) {
                            dst.add(srcArray[j]);
                            nodes[((Integer) srcArray[j]).intValue()] = nodes[dstHashSetIndex];
                        } else {
                            // This is a serious problem
                            System.out.println("Something wrong: set union");
                            System.exit(1);
                        }
                    }
                    allNewEdges.add(curEdge);
                    // add new edge to MST edge vector
                } else {
                    // System.out.println("Nodes are in the same set ... nothing to do here");
                }
            } else {
                // This is a serious problem
                System.out.println("TreeSet should have contained this element!!");
                System.exit(1);
            }
        }
    }

    private boolean nodesAreInDifferentSets(int a, int b) {
        // returns true if graph nodes (a,b) are in different
        // connected components, ie the set for 'a' is different
        // from that for 'b'
        return (!nodes[a].equals(nodes[b]));
    }

    private void printFinalEdges() {
        System.out.println("The minimal spanning tree generated by " +
                "\nKruskal's algorithm is: ");
        while (!allNewEdges.isEmpty()) {
            // for each edge in Vector of MST edges
            Edge e = (Edge) allNewEdges.firstElement();
//            System.out.println("Nodes: (" + e.from + ", " + e.to + ") with cost: " + e.cost);
            System.out.print(e.from + " " + e.to + " " + e.cost + " ");
            allNewEdges.remove(e);
        }
    }

    class Edge implements Comparator {
        // Inner class for representing edge+end-points
        public int from, to, cost;

        public Edge() {
            // Default constructor for TreeSet creation
        }

        public Edge(int f, int t, int c) {
            // Inner class constructor
            from = f;
            to = t;
            cost = c;
        }

        public int compare(Object o1, Object o2) {
            // Used for comparisions during add/remove operations
            int cost1 = ((Edge) o1).cost;
            int cost2 = ((Edge) o2).cost;
            int from1 = ((Edge) o1).from;
            int from2 = ((Edge) o2).from;
            int to1 = ((Edge) o1).to;
            int to2 = ((Edge) o2).to;
            if (cost1 < cost2)
                return (-1);
            else if (cost1 == cost2 && from1 == from2 && to1 == to2)
                return (0);
            else if (cost1 == cost2)
                return (-1);
            else if (cost1 > cost2)
                return (1);
            else
                return (0);
        }

        public boolean equals(Object obj) {
            // Used for comparisions during add/remove operations
            Edge e = (Edge) obj;
            return (cost == e.cost && from == e.from && to == e.to);
        }
    }
}