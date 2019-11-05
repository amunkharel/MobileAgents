package mobileAgents;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;

public class GUI implements Runnable{

    /**
     * instance of class graph
     */
    private Graph graph;

    /**
     * make GUI here
     */
    private Canvas canvas;

    /**
     * use this to draw images
     */
    private GraphicsContext gc;

    /**
     * list of the sensors created
     */
    private ArrayList<Sensor> sensors = new ArrayList<>();

    /**
     * coordinates of base station
     */
    private int stationX;
    private int stationY;

    /**
     * used for scalability
     */
    private int differenceX;
    private int differenceY;
    private int leastX;
    private int leastY;

    /**
     * used to determine the pixel
     * location of base station
     */
    private int stationPixelX;
    private int stationPixelY;

    /**
     * instance of class Log
     */
    private  Log log;


    /**
     * initialize the parameters
     * @param graph
     * @param canvas
     * @param log
     */
    public GUI(Graph graph, Canvas canvas, Log log) {
        this.graph = graph;

        this.canvas = canvas;

        this.gc = canvas.getGraphicsContext2D();

        this.log = log;
    }

    /**
     * set the sensors to be used
     * @param sensors
     */
    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * initialize stationX
     * @param stationX
     */
    public void setStationX(int stationX){this.stationX = stationX;}

    /**
     * initialize stationY
     * @param stationY
     */
    public void setStationY(int stationY){this.stationY = stationY;}


    /**
     * update canvas with back end data
     */
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

            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 30));
            gc.fillText(log.getLogMessage(), 50, 90);
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

    /**
     * bigger sized nodes for small graphs
     * @param i
     */
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

    /**
     * medium sized nodes for medium graphs
     * @param i
     */
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

    /**
     * small sized nodes for big graphs
     * @param i
     */
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


    /**
     * bigger sized edges for small graphs
     * @param i
     * @param j
     */
    public void forOneDigitEdge(int i, int j){
        int startRow = (sensors.get(i).getXCor() - leastX) * 100 + 215;
        int endRow = (sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 100 + 215;
        int startColumn = (differenceY - (sensors.get(i).getYCor() - leastY)) * 100 + 215;
        int endColumn = (differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 100 + 215;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }

    /**
     * medium sized edges for medium graphs
     * @param i
     * @param j
     */
    public void forTwoDigitsEdge(int i, int j){
        int startRow = (sensors.get(i).getXCor() - leastX) * 45 + 207;
        int endRow = (sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 45 + 207;
        int startColumn = (differenceY - (sensors.get(i).getYCor() - leastY)) * 45 + 207;
        int endColumn = (differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 45 + 207;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }

    /**
     * smaller sized edges for large graphs
     * @param i
     * @param j
     */
    public void forThreeDigitsEdge(int i, int j){
        int startRow = ((sensors.get(i).getXCor() - leastX) * 4) + 204;
        int endRow = ((sensors.get(i).getNeighbors().get(j).getXCor() - leastX) * 4) + 204;
        int startColumn = ((differenceY - (sensors.get(i).getYCor() - leastY)) * 4) + 205;
        int endColumn = ((differenceY - (sensors.get(i).getNeighbors().get(j).getYCor() - leastY)) * 4) + 205;
        drawEdges(startRow, startColumn, endRow, endColumn);
    }


    /**
     * give color according to state of node
     * @param row
     * @param column
     * @param state
     * @param hasAgent
     * @param agentNumber
     */
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

    /**
     * if agent present, indicate node with a black outer circle
     * @param row
     * @param column
     * @param agentNumber
     */
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

    /**
     * draw nodes according to graph size
     * @param row
     * @param column
     */
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

    /**
     * draw edges according to graph size
     * @param startRow
     * @param startColumn
     * @param endRow
     * @param endColumn
     */
    public void drawEdges(int startRow, int startColumn, int endRow, int endColumn) {
        gc.setFill(Color.BLACK);
        gc.strokeLine(startRow, startColumn, endRow, endColumn);
    }


    @Override
    /**
     * thread for GUI
     */
    public void run() {
        updateCanvas();
    }

    /**
     * initialize this class's differenceY
     * @param differenceY
     */
    public void setDifferenceY(int differenceY) {
        this.differenceY = differenceY;
    }

    /**
     * initialize this class's differenceX
     * @param differenceX
     */
    public void setDifferenceX(int differenceX) {
        this.differenceX = differenceX;
    }

    /**
     * initialize this class's leastY
     * @param leastY
     */
    public void setLeastY(int leastY) {
        this.leastY = leastY;
    }

    /**
     * initialize this class's leastX
     * @param leastX
     */
    public void setLeastX(int leastX) {
        this.leastX = leastX;
    }
}
