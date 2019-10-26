package mobileAgents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Agent implements Runnable {

    private Sensor sensor;

    private boolean foundYellow;

    private int numberOfNodes;

    private ArrayList<Sensor> sensors = new ArrayList<>();


    public Agent (Sensor sensor, int numberOfNodes) {
        this.sensor = sensor;
        this.foundYellow = false;
        this.numberOfNodes = numberOfNodes;
    }


    @Override
    public void run() {
        randomWalk();
    }

    public void randomWalk() {
        if(!foundYellow) {


            int s = sensor.getId();

            Sensor sensor = this.sensor;

            boolean visited[] = new boolean[this.numberOfNodes];

            LinkedList<Sensor> queue = new LinkedList<Sensor>();


            visited[s] = true;

            queue.add(sensor);

            while (sensor.getState() != 'y') {
                sensor = queue.poll();
                this.sensor = sensor;
                System.out.println("Agent is at sensor " + sensor.getId());


                for (int i = 0; i < sensor.getNeighbors().size(); i++) {
                    int n  = sensor.getNeighbors().get(i).getId();
                    if(!visited[n]){
                        visited[n] = true;
                        queue.add(sensor.getNeighbors().get(i));
                    }
                }

            }


        }
    }
}
