package api.application;

import model.ElevatorState;

/**
 * Handler for WebSocket events from the elevator control system.
 *
 * This class processes incoming messages from the WebSocket connection,
 * parses them using {@link JsonMessageParser}, and manages the current
 * elevator state. It also handles connection lifecycle events (connect/disconnect)
 * and errors.
 */
public class WebSocketHandler {

    /** The current elevator state received from the control system */
    private ElevatorState elevatorState;

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
     * @param code the close code from the WebSocket protocol
     * @param reason the reason for closing the connection
     * @param remote true if the close was initiated by the remote server, false if by the client
     */
    public void onDisconnected(int code, String reason, boolean remote) {
        System.out.println("Disconnected: Code=" + code + ", Reason=" + reason);
    }

    /**
     * Called when a message is received from the WebSocket connection.
     *
     * Parses the incoming JSON message. If it's an error message, it calls
     * {@link #onError(String)}. If it's a status message, updates {@link #elevatorState}.
     *
     * @param rawJson the JSON message received from the control system
     */
    public void onMessage(String rawJson) {
        try {
            if (JsonMessageParser.isError(rawJson)) {
                onError(JsonMessageParser.parseErrorMessage(rawJson));
                return;
            }
            if (JsonMessageParser.isStatusMessage(rawJson)) {
                elevatorState = JsonMessageParser.parseState(rawJson);
            }
        } catch (Exception e) {
            onError(e.getMessage());
        }
    }

    /**
     * Called when an error occurs on the WebSocket connection.
     *
     * Logs the error message for debugging and monitoring purposes.
     *
     * @param exMessage the error message to be logged
     */
    public void onError(String exMessage) {
        System.err.println("WebSocket error: " + exMessage);
    }

    /**
     * Returns the most recently received elevator state.
     *
     * @return the current elevator state, or null if no status has been received yet
     */
    public ElevatorState getElevatorState() {
        return elevatorState;
    }
}
