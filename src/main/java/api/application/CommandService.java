package api.application;

import api.basic.HmiWebSocketClient;

/**
 * Service responsible for sending elevator control commands
 * to the remote control system.
 *
 * This class provides high-level command methods such as:
 * - opening the elevator door
 * - closing the elevator door
 * - resetting the elevator
 *
 * Commands are serialized into JSON format before transmission.
 */
public class CommandService {

    private final HmiWebSocketClient client;

    /**
     * Creates a new CommandService instance.
     *
     * @param client the WebSocket client used for communication with the control system
     */
    public CommandService(HmiWebSocketClient client) {
        this.client = client;
    }

    /**
     * Sends a command to open the elevator door.
     *
     * The command is serialized into JSON and transmitted via WebSocket.
     */
    public void openDoor() {
        sendCommand("OPEN_DOOR");
    }

    /**
     * Sends a command to close the elevator door.
     *
     * The command is serialized into JSON and transmitted via WebSocket.
     */
    public void closeDoor() {
        sendCommand("CLOSE_DOOR");
    }

    /**
     * Sends a command to reset the elevator to its initial state.
     *
     * The command is serialized into JSON and transmitted via WebSocket.
     */
    public void reset() {
        sendCommand("RESET");
    }

    /**
     * Sends a command string to the elevator control system.
     *
     * The command is converted to JSON format using {@link JsonMessageFactory}
     * and transmitted via the WebSocket client.
     *
     * @param command the command string to be sent (e.g., "OPEN_DOOR", "CLOSE_DOOR")
     */
    private void sendCommand(String command) {
        String jsonMessage = JsonMessageFactory.createCommand(command);
        client.send(jsonMessage);
    }
}