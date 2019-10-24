package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Graph {

    private int nodeCounter;   // No. of vertices
    private String file;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private Map<Point, Integer> mappingCoorToInt = new HashMap<>();

    public Graph(String file){
        this.file = file;
    }

    public void readFile(){
        InputStream path = this.getClass().getResourceAsStream(this.file);
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
        System.out.println("Number of vertices: "+nodeCounter);
        printForTesting();
        //breadthFirstSearch(0);
    }

    private void printForTesting() {
        ArrayList<Sensor> neighbors;
        for(int i = 0; i < nodeCounter; i++){
            System.out.println("X: " +sensors.get(i).getXCor());
            System.out.println("Y: " +sensors.get(i).getYCor());
            System.out.println("Sensor ID: " +sensors.get(i).getId());
            neighbors = sensors.get(i).getNeighbors();
            for(int j = 0; j < neighbors.size(); j++){
                //System.out.print("X: " +neighbors.get(j).getXCor());
                //System.out.println(" Y: " +neighbors.get(j).getYCor());
                System.out.println("NeighborID: " +neighbors.get(j).getId());
            }
            System.out.println();
        }
    }


    public void lineEvaluation(String line) {
        if(line.length() >= 8) {
            if (line.substring(0, 4).equals("node")) {
                int verX = line.charAt(5) - '0';
                int verY = line.charAt(7) - '0';
                Sensor sensor = new Sensor(verX, verY, nodeCounter);
                sensors.add(sensor);
                mappingCoorToInt.put(new Point(verX, verY), nodeCounter);

                nodeCounter++;
            } else if (line.substring(0, 4).equals("edge")) {
                int startX = line.charAt(5) - '0';
                int startY = line.charAt(7) - '0';
                int x = getIDofPoint(new Point(startX, startY));
                //System.out.println("X: "+x);
                Sensor sensorOne = sensors.get(x);

                int endX = line.charAt(9) - '0';
                int endY = line.charAt(11) - '0';
                int y = getIDofPoint(new Point(endX, endY));
                //System.out.println("Y: "+y);
                Sensor sensorTwo = sensors.get(y);

                sensorOne.setNeighbors(sensorTwo);
                sensorTwo.setNeighbors(sensorOne);
            } else if (line.substring(0, 4).equals("fire")) {
            } else if (line.substring(0, 7).equals("station")) {
            }
        }
        else{
            //System.out.println("Nothing here");
        }

    }

    public int getIDofPoint(Point p){
        return mappingCoorToInt.get(p);
    }


    /*public void breadthFirstSearch(int s) {
        System.out.println();
        System.out.println("BFS starts here!");
        boolean visited[] = new boolean[nodeCounter];

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
    }*/


}