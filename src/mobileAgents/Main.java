
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


    //private ScrollPane sp = new ScrollPane();

    private String filename = "";

    private ScrollPane sp = new ScrollPane();

    private Canvas canvas = new Canvas(4250, 4250);

    private Graph graph;

    private ArrayList<Sensor> sensors = new ArrayList<>();
    private Log log = new Log();

    private GUI gui = new GUI(graph, canvas, log);

    private Scene scene = new Scene(sp, 4250, 4250);

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private int stationX, stationY;

    private int differenceX, differenceY, leastX, leastY;



    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
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



