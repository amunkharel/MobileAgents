package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Graph {

    private int nodeCounter;   // No. of vertices
    private String file;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private Map<Point, Integer> mappingCoorToInt = new HashMap<>();
    int addingCounter;

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

    public int evaluateNumber(int n, String line, int lineLength){
        int digit = 0;
        int classNum = n;
        while(classNum < lineLength && line.charAt(classNum) > 47 && line.charAt(classNum) < 58){
            digit++;
            classNum++;
        }
        addingCounter = digit;
        int counter = 1;
        while(digit > 1){
            counter *= 10;
            digit--;
        }
        int number = 0;
        while(n < lineLength && line.charAt(n) > 47 && line.charAt(n) < 58){
            number = number + counter * (line.charAt(n) - '0');
            n++;
            counter = counter/10;
        }
        return number;
    }


    public void lineEvaluation(String line) {
        if(line.length() >= 8) {
            if (line.substring(0, 4).equals("node")) {
                int n = 5;
                int verX = evaluateNumber(n, line, line.length());
                //System.out.println("VerX: "+verX);

                n = n + addingCounter + 1;
                int verY = evaluateNumber(n, line, line.length());
                //System.out.println("VerY: "+verY);

                Sensor sensor = new Sensor(verX, verY, nodeCounter);
                sensors.add(sensor);
                mappingCoorToInt.put(new Point(verX, verY), nodeCounter);
                nodeCounter++;
            } else if (line.substring(0, 4).equals("edge")) {
                int n = 5;
                int startX = evaluateNumber(n, line, line.length());
                //System.out.println("startX: "+startX);

                n = n + addingCounter + 1;
                int startY = evaluateNumber(n, line, line.length());
                //System.out.println("startY: "+startY);

                int x = getIDofPoint(new Point(startX, startY));
                Sensor sensorOne = sensors.get(x);
                //System.out.println("X: "+x);

                n = n + addingCounter + 1;
                int endX = evaluateNumber(n, line, line.length());
                //System.out.println("endX: "+endX);

                n = n + addingCounter + 1;
                int endY = evaluateNumber(n, line, line.length());
               //System.out.println("endY: "+endY);

                int y = getIDofPoint(new Point(endX, endY));
                Sensor sensorTwo = sensors.get(y);
                //System.out.println("Y: "+y);

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