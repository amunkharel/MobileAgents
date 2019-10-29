package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Graph {

    private String file;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private Map<Point, Integer> mappingCoorToInt = new HashMap<>();
    private int addingCounter;
    private ArrayList<Point> nodes = new ArrayList<>();
    private ArrayList<Point> startingEdge = new ArrayList<>();
    private ArrayList<Point> endingEdge = new ArrayList<>();
    private ArrayList<Point> fireNodes = new ArrayList<>();
    private int stationX, stationY;

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
        //printForTesting();
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
                n = n + addingCounter + 1;
                int verY = evaluateNumber(n, line, line.length());
                nodes.add(new Point(verX, verY));
            }else if (line.substring(0, 4).equals("edge")) {
                int n = 5;
                int startX = evaluateNumber(n, line, line.length());
                n = n + addingCounter + 1;
                int startY = evaluateNumber(n, line, line.length());
                startingEdge.add(new Point(startX, startY));
                n = n + addingCounter + 1;
                int endX = evaluateNumber(n, line, line.length());
                n = n + addingCounter + 1;
                int endY = evaluateNumber(n, line, line.length());
                endingEdge.add(new Point(endX, endY));
            } else if (line.substring(0, 4).equals("fire")) {
                int n = 5;
                int fireX = evaluateNumber(n, line, line.length());
                n = n + addingCounter + 1;
                int fireY = evaluateNumber(n, line, line.length());
                fireNodes.add(new Point(fireX, fireY));
            }else if (line.substring(0, 7).equals("station")) {
                int n = 8;
                stationX = evaluateNumber(n, line, line.length());
                n = n + addingCounter + 1;
                stationY = evaluateNumber(n, line, line.length());
            }
        }
    }

    public void accessStoredInfoFromFile(){
        for(int i = 0; i < nodes.size(); i++){
            int verX = nodes.get(i).x;
            //System.out.print("VerX: "+verX);
            int verY = nodes.get(i).y;
            //System.out.println(" VerY: "+verY);
            Sensor sensor = new Sensor(verX, verY, i);
            sensors.add(sensor);
            mappingCoorToInt.put(new Point(verX, verY), i);
        }
        for(int i = 0; i < startingEdge.size(); i++){
            int startX = startingEdge.get(i).x;
            //System.out.print("StartX: "+startX);
            int startY = startingEdge.get(i).y;
            //System.out.println(" StartY: "+startY);
            int x = getIDofPoint(new Point(startX, startY));
            Sensor sensorOne = sensors.get(x);

            int endX = endingEdge.get(i).x;
            //System.out.print("EndX: "+endX);
            int endY = endingEdge.get(i).y;
            //System.out.println(" EndY: "+endY);
            int y = getIDofPoint(new Point(endX, endY));
            Sensor sensorTwo = sensors.get(y);

            ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(5);
            sensorOne.setQueues(blockingQueue);
            sensorTwo.setQueues(blockingQueue);

            sensorOne.setNeighbors(sensorTwo);
            sensorTwo.setNeighbors(sensorOne);
        }
        for(int i = 0; i < fireNodes.size(); i++){
            int fireX = fireNodes.get(i).x;
            int fireY = fireNodes.get(i).y;

            int x = getIDofPoint(new Point(fireX, fireY));
            Sensor fireSensor = sensors.get(x);
            fireSensor.setState('r');
        }

        int x = getIDofPoint(new Point(stationX, stationY));
        baseStation = sensors.get(x);
        startingAgent = new Agent(baseStation, sensors.size());
        startingAgent.setFoundYellow(false);
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

        t1 = new Thread(startingAgent);


        t1.start();

        t1.join();

        sensors.get(0).setIsInitialized(true);

        counter = 0;

        agents = startingAgent.getAgents();

        while (baseStation.getState() != 'r') {

            while (counter != 2) {
                for (int i = 0; i < sensors.size(); i++) {
                    t1 = new Thread(sensors.get(i));
                    t1.start();

                }

                t1.join();

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


    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

}