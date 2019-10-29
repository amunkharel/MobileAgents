package mobileAgents;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GUI {

    private Graph graph;

    private Canvas canvas;

    private GraphicsContext gc;

    private ArrayList<Sensor> sensors = new ArrayList<>();


    public GUI(Graph graph, Canvas canvas) {
        this.graph = graph;

        this.canvas = canvas;

        this.gc = canvas.getGraphicsContext2D();
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }


    public void updateCanvas() {
        int row = 0;
        int column = 0;
        char state = 'b';
        boolean hasAgent = false;
        int agentNumber;
        gc.setFill(Color.KHAKI);
        gc.fillRect(0, 0, 1200, 1000);

        for(int i = 0; i < sensors.size(); i++) {

            row = sensors.get(i).getXCor() * 50 + 200;
            column = sensors.get(i).getYCor() * 50 + 200;
            state = sensors.get(i).getState();
            hasAgent = sensors.get(i).hasAgent();
            agentNumber = sensors.get(i).getAgentNumber();

            drawNode(row, column, state, hasAgent, agentNumber);
        }

    }


    public  void drawNode(int row, int column, char state, boolean hasAgent, int agentNumber) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(row, column, 30, 30);

    }

    public void drawEdges() {

    }
}
