package mobileAgents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Agent implements Runnable {

    private Sensor sensor;

    private boolean foundYellow;

    private int numberOfNodes;

    private static  int agentNumber = 0;

    private ArrayList<Sensor> sensors = new ArrayList<>();


    public Agent (Sensor sensor, int numberOfNodes) {
        this.sensor = sensor;
        this.foundYellow = true;
        this.numberOfNodes = numberOfNodes;
    }

    public void setFoundYellow(boolean foundYellow) {
        this.foundYellow = foundYellow;
    }

    @Override
    public void run() {
        try {
            randomWalk();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        checkNeighborAndCloneAgent();

    }

    public void checkNeighborAndCloneAgent() {
        if(foundYellow) {
            System.out.println("Sensor " + sensor.getId());

            for(int i = 0; i < sensor.getNeighbors().size(); i++) {
                if(sensor.getNeighbors().get(i).getState() == 'b') {
                    Agent agent1 = new Agent(sensor.getNeighbors().get(i), numberOfNodes);
                    agentNumber++;
                    sensor.getNeighbors().get(i).addAgent(agentNumber);
                }

                if(sensor.getNeighbors().get(i).getState() == 'y') {
                    recurseYellowNeighbor(sensor.getNeighbors().get(i));
                }

                /*if(sensor.getNeighbors().get(i).getState() == 'y') {
                    Agent agent = new Agent(sensor.getNeighbors().get(i), numberOfNodes);
                    agentNumber++;
                    sensor.getNeighbors().get(i).addAgent(agentNumber);
                } */
            }
        }
    }

    public void recurseYellowNeighbor(Sensor sensor) {
        boolean visited[] = new boolean[numberOfNodes];

        if(visited[sensor.getId()] == true){
            return;
        }
        if(sensor.getState() != 'y'){
            if(sensor.getState() == 'b') {
                Agent agent = new Agent(sensor, numberOfNodes);
                agentNumber++;
                sensor.addAgent(agentNumber);
                visited[sensor.getId()] = true;
                return;
            }
            else if(sensor.getState() == 'r') {
                return;
            }
        }
        else{
            Agent agent = new Agent(sensor, numberOfNodes);
            agentNumber++;
            sensor.addAgent(agentNumber);
            visited[sensor.getId()] = true;
            for(int i = 0; i < sensor.getNeighbors().size(); i++) {
                recurseYellowNeighbor(sensor.getNeighbors().get(i));
            }
        }

    }



    public void randomWalk() throws InterruptedException {
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

                sensor.addAgent(agentNumber);


                for (int i = 0; i < sensor.getNeighbors().size(); i++) {
                    int n  = sensor.getNeighbors().get(i).getId();
                    if(!visited[n]){
                        visited[n] = true;
                        queue.add(sensor.getNeighbors().get(i));
                    }
                }

                Thread.sleep(1000);

                if(sensor.getState() != 'y') {
                    sensor.removeAgent();
                }
                else {
                    foundYellow = true;
                }


            }


        }
    }
}
