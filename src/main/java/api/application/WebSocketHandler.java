package api.application;

import model.ElevatorState;

/**
 * Handler for WebSocket events from the elevator control system.
 *
 * This class processes incoming messages received via the WebSocket
 * connection and delegates state management to the {@link StateService}.
 *
 * Responsibilities:
 * - Handle WebSocket connection lifecycle events
 * - Process incoming JSON messages
 * - Parse elevator state updates using {@link JsonMessageParser}
 * - Forward parsed {@link ElevatorState} objects to the {@link StateService}
 * - Handle and report communication errors
 */
public class WebSocketHandler {

    /** Service responsible for storing and providing the current elevator state */
    private final StateService stateService;

    /**
     * Creates a new WebSocketHandler instance.
     *
     * @param stateService the service responsible for managing elevator states
     */
    public WebSocketHandler(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Called when the WebSocket connection is successfully established.
     *
     * Logs a message indicating successful connection to the control server.
     */
    public void onConnected() {
        System.out.println("Connected to Control Server.");
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * @param code the WebSocket close code
     * @param reason the reason for closing the connection
     * @param remote true if the connection was closed by the remote server,
     *               false if closed locally
     */
    public void onDisconnected(int code, String reason, boolean remote) {
        System.out.println("Disconnected: Code=" + code + ", Reason=" + reason);
    }

    /**
     * Called when a message is received from the WebSocket connection.
     *
     * Error messages are forwarded to {@link #onError(String)}.
     * State messages are parsed into {@link ElevatorState} objects
     * and forwarded to the {@link StateService}.
     *
     * @param rawJson the raw JSON message received from the control system
     */
    public void onMessage(String rawJson) {
        try {

            if (JsonMessageParser.isError(rawJson)) {
                onError(JsonMessageParser.parseErrorMessage(rawJson));
                return;
            }

            if (JsonMessageParser.isStateMessage(rawJson)) {

                ElevatorState elevatorState =
                        JsonMessageParser.parseState(rawJson);

                stateService.updateState(elevatorState);
            }

        } catch (Exception e) {
            onError(e.getMessage());
        }
    }

    /**
     * Called when an error occurs during WebSocket communication
     * or message processing.
     *
     * Logs the error message for debugging and monitoring purposes.
     *
     * @param exMessage the error message describing the failure
     */
    public void onError(String exMessage) {
        System.err.println("WebSocket error: " + exMessage);
    }
}