package mobileAgents;

import java.util.ArrayList;


public class Sensor {

    private int xCor;

    private int yCor;

    private int id;

    private ArrayList<Sensor> neighbors;

    public Sensor(int xcor, int ycor, int id) {
        this.id = id;

        this.xCor = xcor;

        this.yCor = ycor;

        neighbors = new ArrayList<Sensor>();
    }

    public void setNeighbors(Sensor sensor) {
        neighbors.add(sensor);
        /*System.out.println();
        for(int i = 0; i < neighbors.size(); i++) {
            System.out.println("Neighbor ID: " + neighbors.get(i).getId());
        }
        System.out.println();*/
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
}
