package mobileAgents;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;


public class Sensor implements  Runnable{

    /**
     * X coordinate of sensor
     */
    private int xCor;

    /**
     * Y coordinate of sensor
     */
    private int yCor;

    /**
     * id of sensor
     */
    private int id;

    /**
     * sensor has agent or not
     */
    private boolean hasAgent;

    /**
     * agentNumber in the sensor
     */
    private int agentNumber;

    /**
     * list of the sensor's neighbors
     */
    private ArrayList<Sensor> neighbors;

    /**
     * after agent finds yellow node and cloning is done,
     * allows other threads to run
     */
    private static boolean isInitialized;

    /**
     * instance of class Log
     */
    private Log log;

    /**
     * list of blockingQueues for this sensor
     */
    ArrayList<BlockingQueue<String>> queues = new ArrayList<>();

    /**
     * state of sensor, 'y', 'r', 'b'
     */
    private char state;

    /**
     * used after node catches on fire
     */
    private boolean sentMessageAfterBecomingRed;

    /**
     * Initialize a sensor
     * @param xcor
     * @param ycor
     * @param id
     * @param log
     */
    public Sensor(int xcor, int ycor, int id, Log log) {
        this.id = id;
        this.xCor = xcor;
        this.yCor = ycor;
        neighbors = new ArrayList<Sensor>();

        this.log = log;

        state = 'b';

        sentMessageAfterBecomingRed = false;
        isInitialized = false;
    }

    /**
     * set isInitialized to be true
     * @param isInitialized
     */
    public static void setIsInitialized(boolean isInitialized) {
        Sensor.isInitialized = isInitialized;
    }

    /**
     * add neighbors to this sensor
     * @param sensor
     */
    public void setNeighbors(Sensor sensor) {
        neighbors.add(sensor);
    }

    /**
     * @return X coordinate of this sensor
     */
    public int getXCor(){
        return xCor;
    }

    /**
     * @return Y coordinate of this sensor
     */
    public int getYCor(){
        return yCor;
    }

    /**
     * @return ID of this sensor
     */
    public int getId(){
        return id;
    }

    /**
     *
     * @return neighbors of this sensor
     */
    public ArrayList<Sensor> getNeighbors(){
        return neighbors;
    }

    /**
     * set state of this sensor 'b', 'y' or 'r'
     * @param state
     */
    public void setState(char state) {
        this.state = state;
    }

    /**
     * set blocking queues for this sensor
     * @param blockingQueue
     */
    public void setQueues(BlockingQueue<String> blockingQueue) {
        queues.add(blockingQueue);
    }

    @Override
    /**
     * thread for all sensors
     */
    public void run() {
        redNeighborsSentsMessageToNeighbors();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        neigborsOfRedRecieveMessage();

        setFireRandomly();

    }

    /**
     * used to set fire randomly when a yellow node does not have an agent
     */
    public void setFireRandomly() {
        if(isInitialized) {
            if(state == 'r' && sentMessageAfterBecomingRed) {
                for (int i = 0; i < neighbors.size(); i++) {
                    if(!neighbors.get(i).hasAgent()  && neighbors.get(i).getState() != 'r') {
                        int counter = 0;
                        for (int j = 0; j < neighbors.get(i).getNeighbors().size(); j++) {
                            if(!neighbors.get(i).getNeighbors().get(j).hasAgent()) {
                                counter++;
                            }
                        }
                        if(counter == neighbors.get(i).getNeighbors().size()){
                            neighbors.get(i).setState('r');
                            log.setLogMessage("Sensor " + neighbors.get(i).getId() + " is on fire");
                            System.out.println("Sensor " + neighbors.get(i).getId() + " is on fire");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * red node asks it's neighbors to turn yellow
     */
    public void redNeighborsSentsMessageToNeighbors() {
        if(state == 'r' && sentMessageAfterBecomingRed == false) {
            System.out.println("Sensor " + this.id + " is on fire and " +
                    "sending messages to its neighbors");
            log.setLogMessage("Sensor " + this.id + " is on fire and " +
                    "sending messages to its neighbors");
            for(int i = 0; i < neighbors.size(); i++) {
                try {
                    queues.get(i).put("Turn Yellow");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sentMessageAfterBecomingRed = true;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * red node neighbors become yellow
     */
    public void neigborsOfRedRecieveMessage() {
        if(state == 'b') {
            for(int i = 0; i < neighbors.size(); i++) {
                try {

                    if(!queues.get(i).isEmpty()) {

                        for(int j = 0; j < queues.get(i).size(); j++) {
                            if(queues.get(i).contains("Turn Yellow")) {
                                this.state = 'y';
                                System.out.println("Sensor " + this.id + "" +
                                        "knows it's neighbor is on fire");
                                log.setLogMessage("Sensor " + this.id + "" +
                                        "knows it's neighbor is on fire");
                                queues.get(i).remove("Turn Yellow");
                            }
                        }

                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    /**
     * @return state of sensor
     */
    public  char getState() {
        return this.state;
    }

    /**
     * add agent to this sensor
     * @param agentNumber
     */
    public void addAgent(int agentNumber) {
        hasAgent = true;
        this.agentNumber = agentNumber;
        System.out.println("Agent " + agentNumber + " is at sensor " + this.id);
        log.setLogMessage("Agent " + agentNumber + " is at sensor " + this.id);
    }

    /**
     * remove agent from this sensor
     */
    public void removeAgent() {
        hasAgent = false;
    }

    /**
     *
     * @return whether sensor has agent
     */
    public boolean hasAgent() {
        return hasAgent;
    }

    /**
     * get agent number of agent in this sensor
     * @return
     */
    public int getAgentNumber() {
        return agentNumber;
    }


}
