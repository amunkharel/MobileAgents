
package mobileAgents;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    private Text logInfo = new Text();

    private ScrollPane sp = new ScrollPane();

    private Canvas canvas = new Canvas(5000, 5000);

    private Graph graph;

    private ArrayList<Sensor> sensors = new ArrayList<>();

    private GUI gui = new GUI(graph, canvas);

    private Scene scene = new Scene(sp, 5000, 5000);

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private int stationX, stationY;



    public static void main(String[] args) throws InterruptedException {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logInfo.setText("Starting Program ...");
        logInfo.setFont(Font.font("Verdana", 20));

        graph = new Graph("sample.txt");

        graph.readFile();

        graph.accessStoredInfoFromFile();

        sensors = graph.getSensors();
        stationX = graph.getStationX();
        stationY = graph.getStationY();


        gui.setSensors(sensors);
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
            }
        };

        animator.start();
        new Thread(task).start();


        /*try {
            graph.initializeThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/



        //sp.setFitToHeight(true);
        //sp.setFitToWidth(true);
        sp.setContent(canvas);
        //bp.setTop(logInfo);

        //bp.setAlignment(logInfo, Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}



