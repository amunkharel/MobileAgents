
package mobileAgents;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

        //graph.initializeThreads();

        sensors = graph.getSensors();

        gui.setSensors(sensors);


        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gui.updateCanvas();
            }
        };

        animator.start();





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



