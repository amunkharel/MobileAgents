package mobileAgents;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GUI {

    private Graph graph;

    private Canvas canvas;

    private GraphicsContext gc;

    private ArrayList<Sensor> sensors = new ArrayList<>();

    private int stationX;

    private int stationY;


    public GUI(Graph graph, Canvas canvas) {
        this.graph = graph;

        this.canvas = canvas;

        this.gc = canvas.getGraphicsContext2D();
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void setStationX(int stationX){this.stationX = stationX;}

    public void setStationY(int stationY){this.stationY = stationY;}


    public void updateCanvas() {
        System.out.println(stationX);
        System.out.println(stationY);
        int row;
        int column;
        char state;
        boolean hasAgent;
        int agentNumber;
        gc.setFill(Color.KHAKI);
        gc.fillRect(0, 0, 5000, 5000);

        for(int i = 0; i < sensors.size(); i++) {
            row = sensors.get(i).getXCor() * 100 + 200;
            column = (40 - sensors.get(i).getYCor()) * 100 + 200;
            state = sensors.get(i).getState();
            hasAgent = sensors.get(i).hasAgent();
            agentNumber = sensors.get(i).getAgentNumber();
            drawNode(row, column, state, hasAgent, agentNumber);
        }

        int startRow;
        int endRow;
        int startColumn;
        int endColumn;

        for(int i = 0; i < sensors.size(); i++){
            for(int j = 0; j < sensors.get(i).getNeighbors().size(); j++){
                startRow = sensors.get(i).getXCor() * 100 + 215;
                startColumn = (40 - sensors.get(i).getYCor()) * 100 + 215;
                endRow = sensors.get(i).getNeighbors().get(j).getXCor() * 100 + 215;
                endColumn = (40 - sensors.get(i).getNeighbors().get(j).getYCor()) * 100 + 215;
                drawEdges(startRow, startColumn, endRow, endColumn);
            }
        }


        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 30));
        gc.fillText("Waiting for initialization", 30, 4800);
    }


    public  void drawNode(int row, int column, char state, boolean hasAgent, int agentNumber) {
        if(state == 'b'){
            int x = stationX * 100 + 200;
            int y = (40 - stationY) * 100 + 200;
            if(x == row && y == column){
                gc.setFill(Color.GREEN);
                gc.fillOval(row, column, 30, 30);
            }
            else{
                gc.setFill(Color.LIGHTBLUE);
                gc.fillOval(row, column, 30, 30);
            }
        }
        else if(state == 'y'){
            gc.setFill(Color.YELLOW);
            gc.fillOval(row, column, 30, 30);
        }
        else if(state == 'r'){
            gc.setFill(Color.RED);
            gc.fillOval(row, column, 30, 30);
        }

    }

    public void drawEdges(int startRow, int startColumn, int endRow, int endColumn) {
        gc.setFill(Color.BLACK);
        gc.strokeLine(startRow, startColumn, endRow, endColumn);
    }


}
