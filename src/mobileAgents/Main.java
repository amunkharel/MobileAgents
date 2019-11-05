
package mobileAgents;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    /**
     * Useful for scrolling since the display is big.
     * Helps accommodate big graphs like "lol.txt"
     */
    private ScrollPane sp = new ScrollPane();

    /**
     * GUI is made here
     */
    private Canvas canvas = new Canvas(4250, 4250);

    /**
     * used to start methods in class Graph
     */
    private Graph graph;

    /**
     * Array list that contains all the sensors/nodes given
     */
    private ArrayList<Sensor> sensors = new ArrayList<>();

    /**
     * Used to display messages in the GUI
     */
    private Log log = new Log();

    private GUI gui = new GUI(graph, canvas, log);

    /**
     * sets scroll pane in a scene
     */
    private Scene scene = new Scene(sp, 4250, 4250);

    /**
     * stores the co-ordinates of 1 station given in the graph
     */
    private int stationX, stationY;

    /**
     * used for scaling the graph
     */
    private int differenceX, differenceY, leastX, leastY;


    /**
     * start program
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    /**
     * initializes the GUI thread, graph reading,
     * processing and changing is called from here
     */
    public void start(Stage primaryStage) throws Exception {

        Parameters params = getParameters();
        List<String> parameters = params.getRaw();

        if(parameters.size() > 1) {
            System.out.println("The program expects only one parameter");
            System.exit(0);
        }

        graph = new Graph(parameters.get(0), log);
        graph.readFile();
        graph.accessStoredInfoFromFile();
        graph.determineScalibilityOfGraph();

        sensors = graph.getSensors();

        differenceX = graph.getDifferenceX();
        differenceY = graph.getDifferenceY();
        leastX = graph.getLeastX();
        leastY = graph.getLeastY();
        stationX = graph.getStationX();
        stationY = graph.getStationY();

        gui.setSensors(sensors);
        gui.setDifferenceX(differenceX);
        gui.setDifferenceY(differenceY);
        gui.setLeastX(leastX);
        gui.setLeastY(leastY);
        gui.setStationX(stationX);
        gui.setStationY(stationY);

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                graph.initializeThreads();
                return null;
            }
        };

        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gui.updateCanvas();

                if(graph.isGameOver()) {
                    primaryStage.close();
                }
            }
        };

        animator.start();
        new Thread(task).start();
        sp.setContent(canvas);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}



