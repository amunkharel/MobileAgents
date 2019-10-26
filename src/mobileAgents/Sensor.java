package mobileAgents;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;


public class Sensor implements  Runnable{

    private int xCor;

    private int yCor;

    private int id;

    private ArrayList<Sensor> neighbors;

    ArrayList<BlockingQueue<String>> queues = new ArrayList<>();

    private char state;

    private boolean sentMessageAfterBecomingRed;

    public Sensor(int xcor, int ycor, int id) {
        this.id = id;
        this.xCor = xcor;
        this.yCor = ycor;
        neighbors = new ArrayList<Sensor>();

        state = 'b';

        sentMessageAfterBecomingRed = false;
    }

    public void setNeighbors(Sensor sensor) {
        neighbors.add(sensor);
    }

    public int getXCor(){
        return xCor;
    }

    public int getYCor(){
        return yCor;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Sensor> getNeighbors(){
        return neighbors;
    }

    public void setState(char state) {
        this.state = state;
    }

    public void setQueues(BlockingQueue<String> blockingQueue) {
        queues.add(blockingQueue);
    }

    @Override
    public void run() {
        redNeighborsSentsMessageToNeighbors();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        neigborsOfRedRecieveMessage();

    }

    public void redNeighborsSentsMessageToNeighbors() {
        if(state == 'r' && sentMessageAfterBecomingRed == false) {
            System.out.println("Sensor " + this.id + " is on fire and " +
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

    public int getBlockingQueuesize() {
        return this.queues.size();
    }

    public  char getState() {
        return this.state;
    }
}
