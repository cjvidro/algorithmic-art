/**
 * Gui for algorithmic art application
 *
 * @author Charles Vidro
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Gui extends Application {
    private BorderPane mainWindow = new BorderPane();
    private Pane viewer = new Pane();
    private Scene primaryScene;
    private Stage primaryStage;


    public static void main(String[] args) {
        launch(args);
    }
    /**
     * JavaFX setup window
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        mainWindow.setCenter(viewer);

        this.primaryStage = primaryStage;
        primaryScene = new Scene(mainWindow, 1280, 720);

        this.primaryStage.setScene(primaryScene);
        this.primaryStage.setTitle("Algorithmic Art");

        AlgorithmicLine line = new AlgorithmicLine(500, 200, 500, 400, 0, 25, 25, 0, 8);
        line.draw(viewer);


        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }
}
