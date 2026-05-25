package api.application;

import api.basic.WebSocketClient;
import model.ElevatorState;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service responsible for requesting and managing
 * the current elevator state from the control system.
 *
 * This service periodically requests the current elevator state
 * via WebSocket and stores the latest received {@link ElevatorState}.
 */
public class StateService {

    /** Default polling interval in milliseconds */
    private static final long POLLING_INTERVAL_MS = 200;

    /** WebSocket client used for communication with the control system */
    private final WebSocketClient client;

    /** Executor responsible for periodic state polling */
    private final ScheduledExecutorService pollingExecutor;

    /** Most recently received elevator state */
    private volatile ElevatorState currentState;

    private StateListener stateListener;

    /**
     * Creates a new StateService instance.
     *
     * @param client the WebSocket client used for communication
     */
    public StateService(WebSocketClient client) {
        this.client = client;
        this.pollingExecutor =
                Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Sends a state request to the control system.
     *
     * The control system responds with the current
     * {@link ElevatorState} as a JSON message.
     */
    public void requestState() {

        String jsonMessage =
                JsonMessageFactory.createCommand("REQUEST_STATE");

        client.sendMessage(jsonMessage);
    }

    /**
     * Starts automatic periodic state polling.
     *
     * The control system is queried every
     * {@value #POLLING_INTERVAL_MS} milliseconds.
     */
    public void startPolling() {

        pollingExecutor.scheduleAtFixedRate(
                this::requestState,
                0,
                POLLING_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Stops all polling activities immediately.
     */
    public void shutdown() {
        pollingExecutor.shutdownNow();
    }

    /**
     * Updates the currently stored elevator state.
     *
     * @param elevatorState the latest elevator state received
     *                      from the control system
     */
    public void updateState(ElevatorState elevatorState) {
        this.currentState = elevatorState;

        if (stateListener != null) {
            stateListener.onStateUpdated(elevatorState);
        }
    }

    /**
     * Returns the most recently received elevator state.
     *
     * @return the current elevator state,
     *         or null if no state has been received yet
     */
    public ElevatorState getCurrentState() {
        return currentState;
    }

    /**
     * Checks whether a valid elevator state
     * has already been received.
     *
     * @return true if a state is available,
     *         otherwise false
     */
    public boolean hasState() {
        return currentState != null;
    }

    /**
     * Registers the listener that is notified when a new elevator state is received.
     *
     * @param stateListener the listener to register
     */
    public void setStateListener(StateListener stateListener) {
        this.stateListener = stateListener;
    }
}