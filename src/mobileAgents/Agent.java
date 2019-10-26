package mobileAgents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Agent implements Runnable {

    private Sensor sensor;

    private boolean foundYellow;

    private int numberOfNodes;


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
            boolean visited[] = new boolean[this.numberOfNodes];

            LinkedList<Integer> queue = new LinkedList<Integer>();


            visited[s] = true;

            queue.add(s);

            while (sensor.getState() != 'y') {
                s = queue.poll();
                System.out.println("Agent is at sensor " + s);

                for (int i = 0; i < sensor.getNeighbors().size(); i++) {
                    int n  = sensor.getNeighbors().get(i).getId();
                    if(!visited[n]){
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }


        }
    }
}
