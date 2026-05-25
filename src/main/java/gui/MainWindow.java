package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Main entry point of the Elevator HMI application.
 */
public class MainWindow extends Application {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launchGui(args);
    }

    /**
     * Starts the JavaFX application lifecycle.
     *
     * @param args command line arguments
     */
    private static void launchGui(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the main application window.
     *
     * @param stage primary application stage
     */
    @Override
    public void start(Stage stage) {

        Label label = new Label("Elevator HMI started");

        Scene scene = new Scene(label, 400, 300);

        stage.setTitle("Elevator HMI");
        stage.setScene(scene);
        stage.show();
    }
}