package api.application;

import api.basic.HmiWebSocketClient;
import model.ElevatorState;

/**
 * Service responsible for requesting and managing
 * the current elevator status from the control system.
 *
 * The received status information is converted into
 * ElevatorStatus objects for further processing by the HMI.
 */
public class StatusService {

    private final HmiWebSocketClient client;

    private ElevatorState currentStatus;

    /**
     * Creates a new StatusService instance.
     *
     * @param client the WebSocket client used for communication
     */
    public StatusService(HmiWebSocketClient client) {
        this.client = client;
    }

    /**
     * Requests the current elevator status from the control system.
     */
    public void requestStatus() {
        String jsonMessage =
                JsonMessageFactory.createCommand("REQUEST_STATUS");

        client.send(jsonMessage);
    }

    /**
     * Updates the currently stored elevator status.
     *
     * @param status the latest elevator status
     */
    public void updateStatus(ElevatorState status) {
        this.currentStatus = status;
    }

    /**
     * Returns the most recently received elevator status.
     *
     * @return current elevator status
     */
    public ElevatorState getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Checks whether a status has already been received.
     *
     * @return true if a status is available
     */
    public boolean hasStatus() {
        return currentStatus != null;
    }
}
