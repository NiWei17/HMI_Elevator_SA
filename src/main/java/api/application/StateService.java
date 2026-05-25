package api.application;

import model.ElevatorState;

/**
 * Service responsible for managing the current elevator state.
 *
 * This service stores the latest received {@link ElevatorState}.
 */
public class StateService {

    /** Most recently received elevator state */
    private volatile ElevatorState currentState;

    /** Listener notified when a new state is received */
    private volatile StateListener stateListener;

    /**
     * Creates a new StateService instance.
     */
    public StateService() {
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
            stateListener.stateUpdated(elevatorState);
        }
    }

    /**
     * Sets the listener that is notified whenever a new state is received.
     *
     * @param stateListener listener for state updates
     */
    public void setStateListener(StateListener stateListener) {
        this.stateListener = stateListener;
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
}
