package mobileAgents;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Graph {

    private int nodes;   // No. of vertices
    private LinkedList[] adj; //Adjacency Lists
    private ArrayList<Point> vertices = new ArrayList<>();
    private ArrayList<Point> startingEdge = new ArrayList<>();
    private ArrayList<Point> endingEdge = new ArrayList<>();

    public Graph(String file){
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
        System.out.println(nodes);
        System.out.println(vertices);
        System.out.println(startingEdge);
        System.out.println(endingEdge);

        makeNodes();
        formEdges();
        for (int i = 0; i < 10; i++){
            System.out.println(adj[i]);
        }
        breadthFirstSearch(0);
    }

    public void breadthFirstSearch(int s) {
        boolean visited[] = new boolean[nodes];

        LinkedList<Integer> queue = new LinkedList<>();
        visited[s]=true;
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s+":");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
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
            //System.out.println("Point: " +point);
            int n = 0;
            while(n < startingEdge.size()){
                //System.out.println(startingEdge.get(n));
                if(point.equals(startingEdge.get(n))){
                    Point anotherP = endingEdge.get(n);
                    int value = vertices.indexOf(anotherP);
                    adj[i].add(value);
                    //System.out.println("Hagde bhai");
                }
                n++;
            }
            /*n = 0;
            while(n < endingEdge.size()){
                //System.out.println(startingEdge.get(n));
                if(point.equals(endingEdge.get(n))){
                    Point anotherP = startingEdge.get(n);
                    int value = vertices.indexOf(anotherP);
                    adj[i].add(value);
                    //System.out.println("Hagde bhai");
                }
                n++;
            }*/
            i++;
        }
    }

    public void makeNodes() {
        adj = new LinkedList[nodes];
        System.out.println(adj.length);
        for (int i=0; i<nodes; ++i) {
            adj[i] = new LinkedList();
        }
    }

    public void lineEvaluation(String line) {
        if(line.length() >= 8) {
            if (line.substring(0, 4).equals("node")) {
                System.out.println("Found node");
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
                System.out.println("Found edge");
            } else if (line.substring(0, 4).equals("fire")) {
                System.out.println("Found fire");
            } else if (line.substring(0, 7).equals("station")) {
                System.out.println("Found station");
            }
        }
        else{
            System.out.println("Nothing here");
        }
    }
}


        /*Main g = new Main(3);

        /*g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
        g.addEdge(4, 3);
        g.addEdge(4, 4);

        System.out.println("Following is Breadth First Traversal "+
                "(starting from vertex 0)");

        g.BFS(0);*/

    // Constructor
    /*Main(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addEdge(int v,int w)
    {
        adj[v].add(w);
    }

    // prints BFS traversal from a given source s
    void BFS(int s)
    {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s+":");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();

            while (i.hasNext())
            {
                int n = i.next();
                System.out.print("N = "+n+",");
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
            System.out.println();
        }
    }
}*/
