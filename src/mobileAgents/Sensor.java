package mobileAgents;

import java.util.ArrayList;


public class Sensor {

    private int xcor;

    private int ycor;

    private int id;

    private ArrayList<Sensor> neighbors;

    public Sensor(int xcor, int ycor, int id) {
        this.id = id;

        this.xcor = xcor;

        this.ycor = ycor;

        neighbors = new ArrayList<Sensor>();
    }

    public void setNeighbors(Sensor sensor) {
        neighbors.add(sensor);
    }
}
