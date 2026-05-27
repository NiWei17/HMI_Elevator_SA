package api.application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service responsible for periodically requesting the current elevator state.
 */
public class StatePollingService {

    /** Default polling interval in milliseconds */
    private static final long POLLING_INTERVAL_MS = 200;

    /** Sender used for communication with the control system */
    private final WebSocketSender sender;

    /** Executor responsible for periodic state polling */
    private final ScheduledExecutorService pollingExecutor;

    /**
     * Creates a new StatePollingService instance.
     *
     * @param sender sender used for communication with the control system
     */
    public StatePollingService(WebSocketSender sender) {
        this.sender = sender;
        this.pollingExecutor =
                Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Sends a state request to the control system.
     *
     * The control system responds with the current elevator state
     * as a JSON message.
     */
    public void requestState() {
        String jsonMessage =
                JsonMessageFactory.createCommand("REQUEST_STATUS");

        sender.sendMessage(jsonMessage);
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
}
