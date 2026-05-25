package gui;

import api.application.CommandService;
import api.application.StateService;
import api.application.WebSocketHandler;
import api.basic.WebSocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URI;

/**
 * Main entry point of the Elevator HMI application.
 *
 * This class starts JavaFX, loads the main window,
 * initializes the application services and connects
 * the GUI with the WebSocket API.
 */

public class HmiApplication extends Application {

    private static final String SERVER_URI = "ws://localhost:8888";
    private static final String FXML_PATH = "/fxml/main-window.fxml";

    private WebSocketClient webSocketClient;
    private StateService stateService;
public class HmiApplication extends Application {

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

    @Override
    public void stop() {
        if (stateService != null) {
            stateService.shutdown();
        }

        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}