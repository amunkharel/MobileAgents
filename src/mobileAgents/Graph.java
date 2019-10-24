package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Graph {

    private int nodes;   // No. of vertices
    private LinkedList[] adj; //Adjacency Lists
    private ArrayList<Point> vertices = new ArrayList<>();
    private ArrayList<Point> startingEdge = new ArrayList<>();
    private ArrayList<Point> endingEdge = new ArrayList<>();
    private String file;

    public Graph(String file){
        this.file = file;

    }

    public void readFile(){
        InputStream path = this.getClass().getResourceAsStream(file);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(path));
            String line = reader.readLine();
            while(line != null) {
                lineEvaluation(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*System.out.println("Number of vertices: "+nodes);
        System.out.print("These are the vertices: ");
        System.out.println(vertices);
        //System.out.println(startingEdge);
        //System.out.println(endingEdge);

        makeNodes();
        formEdges();
        for (int i = 0; i < nodes; i++){
            System.out.println("Neighbors of Node " +i+ ":" +adj[i]);
        }
        breadthFirstSearch(0);*/
    }


    public void lineEvaluation(String line) {
        if(line.length() >= 8) {
            if (line.substring(0, 4).equals("node")) {
                //System.out.println("Found node");
                int verX = line.charAt(5) - '0';
                int verY = line.charAt(7) - '0';
                vertices.add(new Point(verX, verY));
                nodes++;
            } else if (line.substring(0, 4).equals("edge")) {
                int startX = line.charAt(5) - '0';
                int startY = line.charAt(7) - '0';
                startingEdge.add(new Point(startX, startY));
                int endX = line.charAt(9) - '0';
                int endY = line.charAt(11) - '0';
                endingEdge.add(new Point(endX, endY));
                //System.out.println("Found edge");
            } else if (line.substring(0, 4).equals("fire")) {
                //System.out.println("Found fire");
            } else if (line.substring(0, 7).equals("station")) {
                //System.out.println("Found station");
            }
        }
        else{
            //System.out.println("Nothing here");
        }
    }

    public void breadthFirstSearch(int s) {
        System.out.println();
        System.out.println("BFS starts here!");
        boolean visited[] = new boolean[nodes];

        LinkedList<Integer> queue = new LinkedList<>();
        visited[s]=true;
        queue.add(s);

        while (queue.size() != 0)
        {
            s = queue.poll();
            System.out.print(s+":");
            Iterator<Integer> i = adj[s].listIterator();

            while (i.hasNext())
            {
                int n = i.next();
                System.out.print(" N = "+n+",");
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
            System.out.println();
        }
    }


    public void formEdges() {
        int i = 0;
        while(i < adj.length){
            Point point = vertices.get(i);
            int n = 0;
            while(n < startingEdge.size()){
                if(point.equals(startingEdge.get(n))){
                    Point anotherP = endingEdge.get(n);
                    int value = vertices.indexOf(anotherP);
                    adj[i].add(value);
                }
                n++;
            }
            n = 0;
            while(n < endingEdge.size()){
                if(point.equals(endingEdge.get(n))){
                    Point anotherP = startingEdge.get(n);
                    int value = vertices.indexOf(anotherP);
                    adj[i].add(value);
                }
                n++;
            }
            i++;
        }
    }

    public void makeNodes() {
        adj = new LinkedList[nodes];
        //System.out.println(adj.length);
        for (int i=0; i<nodes; ++i) {
            adj[i] = new LinkedList();
        }
    }

}