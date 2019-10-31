package mobileAgents;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;

public class GUI implements Runnable{

    private Graph graph;

    private Canvas canvas;

    private GraphicsContext gc;

    private ArrayList<Sensor> sensors = new ArrayList<>();

    private int stationX;

    private int stationY;

    private int differenceX;
    private int differenceY;
    private int leastX;
    private int leastY;
    private int stationPixelX;
    private int stationPixelY;


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
        gc.setFill(Color.KHAKI);
        gc.fillRect(0, 0, 4250, 4250);

        for(int i = 0; i < sensors.size(); i++) {
            if(differenceX >= 0 && differenceX <= 9){
                if(differenceY >= 0 && differenceY <= 9) {
                    forOneDigitNode(i);
                }
                else if (differenceY >= 10 && differenceY <= 99){
                    forTwoDigitSNode(i);
                }
                else if(differenceY >= 100 && differenceY <= 999){
                    forThreeDigitsNode(i);
                }
            }
            else if(differenceX >= 10 && differenceX <= 99){
                if(differenceY >= 0 && differenceY <= 9) {
                    forTwoDigitSNode(i);
                }
                else if(differenceY >= 10 && differenceY <= 99){
                    forTwoDigitSNode(i);
                }
                else if(differenceY >= 100 && differenceY <= 999){
                    forThreeDigitsNode(i);
                }
            }
            else if (differenceX >= 100 && differenceX <= 999){
                if(differenceY >= 0 && differenceY <= 9) {
                    forThreeDigitsNode(i);
                }
                else if(differenceY >= 10 && differenceY <= 99){
                    forThreeDigitsNode(i);
                }
                else if(differenceY >= 100 && differenceY <= 999){
                    forThreeDigitsNode(i);
                }
            }

            /*gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 30));
            gc.fillText(sensors.get(i).getText(), 50, 90);*/
        }

        for(int i = 0; i < sensors.size(); i++){
            for(int j = 0; j < sensors.get(i).getNeighbors().size(); j++){
                if(differenceX >= 0 && differenceX <= 9){
                    if(differenceY >= 0 && differenceY <= 9) {
                        forOneDigitEdge(i, j);
                    }
                    else if(differenceY >= 10 && differenceY <= 99){
                        forTwoDigitsEdge(i, j);
                    }
                    else if(differenceY >= 100 && differenceY <= 999){
                        forThreeDigitsEdge(i, j);
                    }
                }
                else if(differenceX >= 10 && differenceX <= 99){
                    if(differenceY >= 0 && differenceY <= 9) {
                        forTwoDigitsEdge(i, j);
                    }
                    else if(differenceY >= 10 && differenceY <= 99){
                        forTwoDigitsEdge(i, j);
                    }
                    else if(differenceY >= 100 && differenceY <= 999){
                        forThreeDigitsEdge(i, j);
                    }
                }
                else if (differenceX >= 100 && differenceX <= 999){
                    if(differenceY >= 0 && differenceY <= 9) {
                        forThreeDigitsEdge(i, j);
                    }
                    else if(differenceY >= 10 && differenceY <= 99){
                        forThreeDigitsEdge(i, j);
                    }
                    else if(differenceY >= 100 && differenceY <= 999){
                        forThreeDigitsEdge(i, j);
                    }
                }
            }
        }
    }

    public void forOneDigitNode(int i){
        if (stationX == sensors.get(i).getXCor() && stationY == sensors.get(i).getYCor()){
            stationPixelX = ((sensors.get(i).getXCor() - leastX) * 100) + 200;
            stationPixelY = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 100) + 200;
        }
        int row = ((sensors.get(i).getXCor() - leastX) * 100) + 200;
        int column = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 100) + 200;
        char state = sensors.get(i).getState();
        boolean hasAgent = sensors.get(i).hasAgent();
        int agentNumber = sensors.get(i).getAgentNumber();
        drawNode(row, column, state, hasAgent, agentNumber);
    }

    public void forTwoDigitSNode(int i){
        if (stationX == sensors.get(i).getXCor() && stationY == sensors.get(i).getYCor()){
            stationPixelX = ((sensors.get(i).getXCor() - leastX) * 45) + 200;
            stationPixelY = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 45) + 200;
        }
        int row = ((sensors.get(i).getXCor() - leastX) * 45) + 200;
        int column = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 45) + 200;
        char state = sensors.get(i).getState();
        boolean hasAgent = sensors.get(i).hasAgent();
        int agentNumber = sensors.get(i).getAgentNumber();
        drawNode(row, column, state, hasAgent, agentNumber);
    }

    public void forThreeDigitsNode(int i){
        if (stationX == sensors.get(i).getXCor() && stationY == sensors.get(i).getYCor()){
            stationPixelX = ((sensors.get(i).getXCor() - leastX) * 4) + 200;
            stationPixelY = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 4) + 200;
        }
        int row = ((sensors.get(i).getXCor() - leastX) * 4) + 200;
        int column = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 4) + 200;
        char state = sensors.get(i).getState();
        boolean hasAgent = sensors.get(i).hasAgent();
        int agentNumber = sensors.get(i).getAgentNumber();
        drawNode(row, column, state, hasAgent, agentNumber);
    }



    public void forOneDigitEdge(int i, int j){
        int startRow = (sensors.get(i).getXCor() - leastX) * 100 + 215;
        int endRow = (sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 100 + 215;
        int startColumn = (differenceY - (sensors.get(i).getYCor() - leastY)) * 100 + 215;
        int endColumn = (differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 100 + 215;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }

    public void forTwoDigitsEdge(int i, int j){
        int startRow = (sensors.get(i).getXCor() - leastX) * 45 + 207;
        int endRow = (sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 45 + 207;
        int startColumn = (differenceY - (sensors.get(i).getYCor() - leastY)) * 45 + 207;
        int endColumn = (differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 45 + 207;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }

    public void forThreeDigitsEdge(int i, int j){
        int startRow = ((sensors.get(i).getXCor() - leastX) * 4) + 204;
        int endRow = ((sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 4) + 204;
        int startColumn = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 4) + 205;
        int endColumn = ((differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 4) + 205;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }


    public  void drawNode(int row, int column, char state, boolean hasAgent, int agentNumber) {
        if(state == 'b'){
            if(stationPixelX == row && stationPixelY == column){
                gc.setFill(Color.GREEN);
                drawCircle(row, column);
            }
            else {
                gc.setFill(Color.LIGHTBLUE);
                drawCircle(row, column);
            }
        }
        else if(state == 'y'){
            gc.setFill(Color.YELLOW);
            drawCircle(row, column);
        }
        else if(state == 'r'){
            gc.setFill(Color.RED);
            drawCircle(row, column);
        }

        if(hasAgent == true){
            drawOuterCircle(row, column, agentNumber);
        }
    }

    public void drawOuterCircle(int row, int column, int agentNumber){
        gc.setStroke(Color.BLACK);
        if (differenceX >= 0 && differenceX <= 9) {
            if(differenceY >= 0 && differenceY <= 9) {
                gc.strokeOval(row, column, 30, 30);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(30.0));
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.strokeOval(row, column, 15, 15);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(10.0));
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.strokeOval(row, column, 10, 10);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(7.0));
            }
        }
        else if(differenceX >= 10 && differenceX <= 99){
            if(differenceY >= 0 && differenceY <= 9) {
                gc.strokeOval(row, column, 15, 15);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(10.0));
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.strokeOval(row, column, 15, 15);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(10.0));
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.strokeOval(row, column, 10, 10);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(7.0));
            }
        }
        else if(differenceX >= 100 && differenceX <= 999){
            if(differenceY >= 0 && differenceY <= 9) {
                gc.strokeOval(row, column, 10, 10);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(7.0));
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.strokeOval(row, column, 10, 10);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(7.0));
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.strokeOval(row, column, 10, 10);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(7.0));
            }
        }
        gc.fillText("Agent " + agentNumber, row, column);
    }

    public void drawCircle(int row, int column) {
        if (differenceX >= 0 && differenceX <= 9) {
            if(differenceY >= 0 && differenceY <= 9) {
                gc.fillOval(row, column, 30, 30);
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.fillOval(row, column, 15, 15);
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.fillOval(row, column, 10, 10);
            }
        }
        else if(differenceX >= 10 && differenceX <= 99){
            if(differenceY >= 0 && differenceY <= 9) {
                gc.fillOval(row, column, 15, 15);
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.fillOval(row, column, 15, 15);
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.fillOval(row, column, 10, 10);
            }
        }
        else if(differenceX >= 100 && differenceX <= 999){
            if(differenceY >= 0 && differenceY <= 9) {
                gc.fillOval(row, column, 10, 10);
            }
            else if(differenceY >= 10 && differenceY <= 99){
                gc.fillOval(row, column, 10, 10);
            }
            else if(differenceY >= 100 && differenceY <= 999){
                gc.fillOval(row, column, 10, 10);
            }
        }
    }

    public void drawEdges(int startRow, int startColumn, int endRow, int endColumn) {
        gc.setFill(Color.BLACK);
        gc.strokeLine(startRow, startColumn, endRow, endColumn);
    }


    @Override
    public void run() {
        updateCanvas();
    }

    public void setDifferenceY(int differenceY) {
        this.differenceY = differenceY;
    }

    public void setDifferenceX(int differenceX) {
        this.differenceX = differenceX;
    }

    public void setLeastY(int leastY) {
        this.leastY = leastY;
    }

    public void setLeastX(int leastX) {
        this.leastX = leastX;
    }
}
