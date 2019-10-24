package mobileAgents;

import java.util.ArrayList;

public class Agent {

    private Sensor sensor;

    private ArrayList<Sensor> sensors;

    public Agent (Sensor sensor, ArrayList<Sensor> sensors) {
        this.sensor = sensor;
        this.sensors = sensors;
    }


}
