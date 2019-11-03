package mobileAgents;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Agent implements Runnable {

    private Sensor sensor;

    private boolean foundYellow;

    private int numberOfNodes;

    private static  int agentNumber = 0;

    private  Log log;

    private static Lock lock;

    private ArrayList<Sensor> sensors = new ArrayList<>();

    private static ArrayList<Sensor> agents = new ArrayList<>();


    public Agent (Sensor sensor, int numberOfNodes, Log log) {
        this.sensor = sensor;
        this.foundYellow = true;
        this.numberOfNodes = numberOfNodes;
        lock = new ReentrantLock();
        this.log = log;
    }

    public void setFoundYellow(boolean foundYellow) {
        this.foundYellow = foundYellow;
    }

    @Override
    public void run() {
        try {
            randomWalk();
            checkNeighborAndCloneAgent();
            setAgentOnFire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }

    public void setAgentOnFire() throws InterruptedException{
            Sensor sensor = this.sensor;
            sensor.removeAgent();
            sensor.setState('r');

            for(int i = 0; i < agents.size(); i++) {
                if(agents.get(i).getId() == sensor.getId()) {
                    agents.remove(i);
                }
            }




    }

    public void checkNeighborAndCloneAgent()  {
            if(foundYellow) {
                System.out.println("Sensor " + sensor.getId() + " Agent A" + sensor.getAgentNumber() );

                for(int i = 0; i < sensor.getNeighbors().size(); i++) {
                    if(sensor.getNeighbors().get(i).getState() == 'b' &&
                            !sensor.getNeighbors().get(i).hasAgent()) {
                        Agent agent1 = new Agent(sensor.getNeighbors().get(i), numberOfNodes, log);
                        agentNumber++;
                        sensor.getNeighbors().get(i).addAgent(agentNumber);
                        agents.add(sensor.getNeighbors().get(i));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if(sensor.getNeighbors().get(i).getState() == 'y') {
                        recurseYellowNeighbor(sensor.getNeighbors().get(i));
                    }

                }
            }





    }



    public void recurseYellowNeighbor(Sensor sensor) {
        if(sensor.hasAgent() == true){
            return;
        }
        if(sensor.getState() != 'y'){
            if(sensor.getState() == 'b') {
                Agent agent = new Agent(sensor, numberOfNodes, log);
                agentNumber++;
                sensor.addAgent(agentNumber);
                agents.add(sensor);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return;
            }
            else if(sensor.getState() == 'r') {
                return;
            }
        }
        else{
            Agent agent = new Agent(sensor, numberOfNodes, log);
            agentNumber++;
            sensor.addAgent(agentNumber);
            agents.add(sensor);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                    agents.add(sensor);
                    foundYellow = true;
                }


            }


        }
    }

    public ArrayList<Sensor> getAgents() {
        return agents;
    }
}
