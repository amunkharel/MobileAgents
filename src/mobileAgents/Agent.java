package mobileAgents;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Agent implements Runnable {

    /**
     * instance of class Sensor
     */
    private Sensor sensor;

    /**
     * boolean which indicates
     * state of node, if yellow, return true
     * if not, return false
     */
    private boolean foundYellow;

    /**
     * number of nodes in the graph
     */
    private int numberOfNodes;

    /**
     * agent ID
     */
    private static  int agentNumber = 0;

    /**
     * instance of class Log
     */
    private  Log log;

    private static Lock lock;

    /**
     * agents made in the threads
     */
    private static ArrayList<Sensor> agents = new ArrayList<>();


    /**
     * give identity to an agent
     * @param sensor
     * @param numberOfNodes
     * @param log
     */
    public Agent (Sensor sensor, int numberOfNodes, Log log) {
        this.sensor = sensor;
        this.foundYellow = true;
        this.numberOfNodes = numberOfNodes;
        lock = new ReentrantLock();
        this.log = log;
    }

    /**
     * at the start, this.foundYellow is true
     * @param foundYellow
     */
    public void setFoundYellow(boolean foundYellow) {
        this.foundYellow = foundYellow;
    }

    @Override
    /**
     * thread for each sensor
     */
    public void run() {
        try {
            randomWalk();
            checkNeighborAndCloneAgent();
            setAgentOnFire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * kill agent on a node on fire
     * @throws InterruptedException
     */
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

    /**
     * agent clones itself recursively
     */
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


    /**
     * called by above function. helper method where most of the recursion
     * is done.
     * @param sensor
     */
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


    /**
     * initial method employed by the agent to find a yellow node. BFS used
     * @throws InterruptedException
     */
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

    /**
     * return agents formed
     * @return
     */
    public ArrayList<Sensor> getAgents() {
        return agents;
    }
}
