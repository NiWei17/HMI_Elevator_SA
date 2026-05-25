package api.application;

import model.ElevatorState;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class responsible for parsing incoming JSON messages
 * received from the elevator control system.
 *
 * This class converts raw JSON responses into Java objects
 * such as ElevatorStatus instances.
 *
 * Supported message formats:
 * - Status messages: Contains doorOpened, doorClosed, motorReady, motorOn, motorError, cycles, id
 * - Success responses: status field set to "OK"
 * - Error responses: status field set to "ERROR" with optional error message
 */
public final class JsonMessageParser {

    private JsonMessageParser() {
    }

    /**
     * Checks if the message indicates a successful operation.
     *
     * Looks for a "status" field (in header or root) with value "OK".
     *
     * @param rawJson the JSON response string from the control system
     * @return true if the response status is "OK"
     * @throws JSONException if the JSON is malformed
     */
    public static boolean isSuccess(String rawJson) {
        try {
            return "OK".equalsIgnoreCase(getStateValue(rawJson));
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Checks if the message indicates an error.
     *
     * Looks for a "status" field (in header or root) with value "ERROR".
     *
     * @param rawJson the JSON response string from the control system
     * @return true if the response status is "ERROR"
     * @throws JSONException if the JSON is malformed
     */
    public static boolean isError(String rawJson) {
        try {
            return "ERROR".equalsIgnoreCase(getStateValue(rawJson));
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Extracts the status field value from the JSON message.
     *
     * Checks for status in header first, then in root.
     *
     * @param rawJson the JSON response string
     * @return the status value (e.g., "OK", "ERROR"), or empty string if not found
     */
    private static String getStateValue(String rawJson) {
        JSONObject root = new JSONObject(rawJson);

        if (root.has("header")) {
            String headerStatus = root.getJSONObject("header").optString("status");
            if (!headerStatus.isEmpty()) {
                return headerStatus;
            }
        }

        return root.optString("status", "");
    }

    /**
     * Extracts the error message from an error response.
     *
     * Looks for an "error" field in the payload or root.
     *
     * @param rawJson the JSON error response string
     * @return the error message, or "Unknown error" if not found
     * @throws JSONException if the JSON is malformed
     */
    public static String parseErrorMessage(String rawJson) {
        JSONObject root = new JSONObject(rawJson);

        if (root.has("payload")) {
            String error = root.getJSONObject("payload").optString("error");
            if (!error.isEmpty()) {
                return error;
            }
        }

        return root.optString("error", "Unknown error");
    }

    /**
     * Checks if the message is a status update from the control system.
     *
     * A valid status message must contain all required elevator state fields:
     * doorOpened, doorClosed, motorReady, motorOn, motorError, cycles, id
     *
     * @param rawJson the JSON message string
     * @return true if the message contains all required status fields
     * @throws JSONException if the JSON is malformed
     */
    public static boolean isStatusMessage(String rawJson) {
        try {
            JSONObject root = new JSONObject(rawJson);

            // Check if header indicates status message
            if (root.has("header")) {
                if ("STATUS".equalsIgnoreCase(root.getJSONObject("header").optString("type"))) {
                    return true;
                }
            }

            // Fallback: check for required status fields in root or payload
            JSONObject statusData = root;
            if (root.has("payload")) {
                statusData = root.getJSONObject("payload");
            }

            return statusData.has("doorOpened")
                    && statusData.has("doorClosed")
                    && statusData.has("motorReady")
                    && statusData.has("motorOn")
                    && statusData.has("motorError")
                    && statusData.has("cycles")
                    && statusData.has("id");
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Parses a status message into an ElevatorState object.
     *
     * Expected structure (in payload or root):
     * {
     *   "doorOpened": boolean,
     *   "doorClosed": boolean,
     *   "motorReady": boolean,
     *   "motorOn": boolean,
     *   "motorError": boolean,
     *   "cycles": integer,
     *   "id": integer
     * }
     *
     * @param rawJson the JSON status message string
     * @return an ElevatorStatus object with the parsed values
     * @throws IllegalArgumentException if the message format is invalid or required fields are missing
     */
    public static ElevatorState parseState(String rawJson) {
        try {
            JSONObject root = new JSONObject(rawJson);

            JSONObject statusData;
            if (root.has("payload")) {
                statusData = root.getJSONObject("payload");
            } else {
                statusData = root;
            }

            return new ElevatorState(
                    statusData.getBoolean("doorOpened"),
                    statusData.getBoolean("doorClosed"),
                    statusData.getBoolean("motorReady"),
                    statusData.getBoolean("motorOn"),
                    statusData.getBoolean("motorError"),
                    statusData.getInt("cycles"),
                    statusData.getInt("id")
            );
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid status message format: " + rawJson, e);
        }
    }
}