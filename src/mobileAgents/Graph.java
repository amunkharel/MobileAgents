package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Graph {

    private int nodeCounter;   // No. of vertices
    private String file;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private Map<Point, Integer> mappingCoorToInt = new HashMap<>();
    int addingCounter;

    private Agent startingAgent = null;

    private Sensor baseStation = null;

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
        for(int i = 0; i < sensors.size(); i++){
            System.out.println("X: " +sensors.get(i).getXCor());
            System.out.println("Y: " +sensors.get(i).getYCor());
            System.out.println("Sensor ID: " +sensors.get(i).getId());
            neighbors = sensors.get(i).getNeighbors();
            for(int j = 0; j < neighbors.size(); j++){
                //System.out.print("X: " +neighbors.get(j).getXCor());
                //System.out.println(" Y: " +neighbors.get(j).getYCor());
                System.out.println("NeighborID: " +neighbors.get(j).getId());
            }
            System.out.println("Blocking Queue size: " + sensors.get(i).getBlockingQueuesize());
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

                ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(5);

                sensorOne.setQueues(blockingQueue);
                sensorTwo.setQueues(blockingQueue);

                sensorOne.setNeighbors(sensorTwo);
                sensorTwo.setNeighbors(sensorOne);

            } else if (line.substring(0, 4).equals("fire")) {

                int n = 5;
                int fireX = evaluateNumber(n, line, line.length());

                n = n + addingCounter + 1;
                int fireY = evaluateNumber(n, line, line.length());

                int x = getIDofPoint(new Point(fireX, fireY));
                Sensor fireSensor = sensors.get(x);

                fireSensor.setState('r');


            } else if (line.substring(0, 7).equals("station")) {
                int n = 5;
                int stationX = evaluateNumber(n, line, line.length());

                n = n + addingCounter + 1;
                int stationY = evaluateNumber(n, line, line.length());

                int x = getIDofPoint(new Point(stationX, stationY));
                baseStation = sensors.get(x);
                startingAgent = new Agent(baseStation, sensors.size());
                startingAgent.setFoundYellow(false);

            }
        }
        else{
        }

    }

    public void initializeThreads() throws  InterruptedException{

        ArrayList<Sensor> agents = new ArrayList<>();
        Thread t1 = null;
        int counter = 0;

        while (counter != 2) {
            for (int i = 0; i < sensors.size(); i++) {
                t1 = new Thread(sensors.get(i));
                t1.start();


            }
            counter++;

            t1.join();

        }
        sensors.get(0).setIsInitialized(true);
        t1 = new Thread(startingAgent);


        t1.start();

        t1.join();

        counter = 0;

        agents = startingAgent.getAgents();

        while (baseStation.getState() != 'r') {

            while (counter != 2) {
                for (int i = 0; i < sensors.size(); i++) {
                    t1 = new Thread(sensors.get(i));
                    t1.start();
                    t1.join();
                }


                counter++;
            }

            counter = 0;



            Agent agent = new Agent(agents.get(0), sensors.size());
            t1 = new Thread(agent);
            t1.start();
            t1.join();


        }




    }

    public int getIDofPoint(Point p){
        return mappingCoorToInt.get(p);
    }


}