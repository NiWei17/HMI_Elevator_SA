package gui;

import api.application.CommandService;
import api.application.StatePollingService;
import api.application.StateService;
import api.application.WebSocketHandler;
import api.basic.WebSocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URI;
import java.util.concurrent.TimeUnit;

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
    private StatePollingService statePollingService;

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
     * @throws Exception if the UI or WebSocket initialization fails
     */
    @Override
    public void start(Stage stage) throws Exception {
        StateService stateService = new StateService();
        WebSocketHandler webSocketHandler = new WebSocketHandler(stateService);

        webSocketClient = new WebSocketClient(
                new URI(SERVER_URI),
                webSocketHandler
        );

        CommandService commandService = new CommandService(webSocketClient);
        statePollingService = new StatePollingService(webSocketClient);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        Scene scene = new Scene(loader.load());

        MainWindowEventHandler controller = loader.getController();
        controller.setCommandService(commandService);
        controller.setStateService(stateService);

        stage.setTitle("Elevator HMI");
        stage.setScene(scene);
        stage.show();

        if (webSocketClient.connectBlocking(5, TimeUnit.SECONDS)) {
            statePollingService.startPolling();
        }
    }

    @Override
    public void stop() {
        if (statePollingService != null) {
            statePollingService.shutdown();
        }

        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
