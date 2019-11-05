package mobileAgents;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Graph {
    /**
     * name of file
     */
    private String file;

    /**
     * Array list that contains all the sensors/nodes given
     */
    private ArrayList<Sensor> sensors = new ArrayList<>();

    /**
     * useful for storing id of a node, the first node read in
     * the file has id 0, the last node read has id n - 1
     */
    private Map<Point, Integer> mappingCoorToInt = new HashMap<>();
    /**
     * used when reading lines
     */
    private int addingCounter;
    /**
     * Array list that stores the nodes given in config file
     */
    private ArrayList<Point> nodes = new ArrayList<>();
    /**
     * Array list that stores the first coordinate given
     * in the sentence that starts with "edge"
     */
    private ArrayList<Point> startingEdge = new ArrayList<>();
    /**
     * Array list that stores the second coordinate given
     * in the sentence that starts with "edge"
     */
    private ArrayList<Point> endingEdge = new ArrayList<>();
    /**
     * Array list that stores the fire nodes given in
     * config file
     */
    private ArrayList<Point> fireNodes = new ArrayList<>();
    private ArrayList<Point> baseStationNodes = new ArrayList<>();
    /**
     * coordinates of base station
     */
    private int stationX, stationY;

    /**
     * variables used for scaling
     */
    private int differenceX = 0;
    private int differenceY = 0;
    private int leastX = 10000;
    private int leastY = 10000;

    /**
     * instance of class Log
     */
    private  Log log;

    /**
     * determines if game is over or not
     */
    private boolean gameOver = false;

    /**
     * agent that gets sent to the fire at the start
     */
    private Agent startingAgent = null;
    /**
     * baseStation sensor
     */
    private Sensor baseStation = null;

    /**
     * @param file read config file
     * @param log
     */
    public Graph(String file, Log log){
        this.file = file;
        this.log = log;
    }

    /**
     * read the config file.
     */
    public void readFile(){
        String pathname = "resources/" + file;

        try {
            Scanner sc = new Scanner(new File(pathname));
            while(sc.hasNext()) {
                String str = sc.nextLine();
                if(!str.isEmpty()) {
                    lineEvaluation(str);
                }
            }

            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * parsing coordinates given in file from
     * string to integer
     * @param n
     * @param line
     * @param lineLength
     * @return
     */
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

    /**
     * Evaluate each line of a file by how it starts
     * add coordinates to the appropriate array list
     * defined in this class.
     * @param line
     */
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
                baseStationNodes.add(new Point(stationX, stationY));
            }
        }
    }

    /**
     * initialize sensors, form neighbors, blocking queues
     * for each sensor, initialize fire nodes, store
     * coordinates of base station
     */
    public void accessStoredInfoFromFile(){
        for(int i = 0; i < nodes.size(); i++){
            int verX = nodes.get(i).x;
            int verY = nodes.get(i).y;
            Sensor sensor = new Sensor(verX, verY, i, log);
            sensors.add(sensor);
            mappingCoorToInt.put(new Point(verX, verY), i);
        }
        for(int i = 0; i < startingEdge.size(); i++){
            int startX = startingEdge.get(i).x;
            int startY = startingEdge.get(i).y;
            int x = getIDofPoint(new Point(startX, startY));
            Sensor sensorOne = sensors.get(x);

            int endX = endingEdge.get(i).x;
            int endY = endingEdge.get(i).y;
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
        startingAgent = new Agent(baseStation, sensors.size(), log);
        startingAgent.setFoundYellow(false);

    }

    /**
     * Initialize the threads of all sensors and agents
     * created in the program.
     * @throws InterruptedException
     */
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



            Agent agent = new Agent(agents.get(0), sensors.size(), log);
            t1 = new Thread(agent);
            t1.start();
            t1.join();


        }

        gameOver = true;

    }

    /**
     * @param p
     * @return ID of coordinate
     */
    public int getIDofPoint(Point p){
        return mappingCoorToInt.get(p);
    }


    /**
     * @return the sensors created
     */
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    /**
     * @return X coordinate of base station
     */
    public int getStationX(){
        return stationX;
    }

    /**
     *
     * @return Y coordinate of base station
     */
    public int getStationY(){
        return stationY;
    }

    /**
     * determine the size of the graph in terms of the
     * difference between the smallest and the biggest node,
     * both vertically and horizontally
     */
    public void determineScalibilityOfGraph() {
        setDifferenceX();
        setDifferenceY();
    }

    /**
     * determine the size of the graph horizontally
     * and the coordinate of the lowest node
     */
    private void setDifferenceX() {
        int difference = 0;
        for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.size(); j++){
                difference = Math.abs(nodes.get(j).x - nodes.get(i).x);
                if(nodes.get(j).x < leastX){
                    leastX = nodes.get(j).x;
                }
                if(nodes.get(i).x < leastX){
                    leastX = nodes.get(i).x;
                }
                if(difference > differenceX){
                    differenceX = difference;
                }
            }
        }
    }

    /**
     *
     * @return least X coordinate
     */
    public int getLeastX(){
        return leastX;
    }

    /**
     *
     * @return horizontal size difference
     */
    public int getDifferenceX(){
        return differenceX;
    }

    /**
     * determine the size of the graph vertically
     * and the coordinate of the lowest node
     */
    private void setDifferenceY() {
        int difference = 0;
        for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < nodes.size(); j++){
                difference = Math.abs(nodes.get(j).y - nodes.get(i).y);
                if(nodes.get(j).y < leastY){
                    leastY = nodes.get(j).y;
                }
                if(nodes.get(i).y < leastY){
                    leastY = nodes.get(i).y;
                }
                if(difference > differenceY){
                    differenceY = difference;
                }
            }
        }
    }

    /**
     * @return least Y coordinate
     */
    public int getLeastY(){
        return leastY;
    }


    /**
     *
     * @return vertical size difference
     */
    public int getDifferenceY(){
        return differenceY;
    }

    /**
     * @return state of game
     */
    public boolean isGameOver() {
        return gameOver;
    }
}