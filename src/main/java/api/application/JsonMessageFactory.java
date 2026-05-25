package api.application;

import org.json.JSONObject;

/**
 * Utility class responsible for creating JSON messages
 * that are sent to the elevator control system.
 *
 * All outgoing WebSocket messages should be created
 * through this class to ensure consistent formatting.
 *
 * Available commands:
 * - "OPEN_DOOR": Opens the elevator door
 * - "CLOSE_DOOR": Closes the elevator door
 * - "RESET": Resets the elevator to its initial state
 * - "REQUEST_STATUS": Requests the current elevator status
 */
public final class JsonMessageFactory {

    private JsonMessageFactory() {
    }

    /**
     * Creates a JSON command message with the given command string.
     *
     * Supported command strings:
     * - "OPEN_DOOR": Opens the elevator door
     * - "CLOSE_DOOR": Closes the elevator door
     * - "RESET": Resets the elevator to its initial state
     * - "REQUEST_STATUS": Requests the current elevator status
     *
     * @param command the command string to be wrapped in JSON
     * @return JSON string representation of the command
     */
    public static String createCommand(String command) {
        JSONObject json = new JSONObject();
        json.put("command", command);
        return json.toString();
    }

}