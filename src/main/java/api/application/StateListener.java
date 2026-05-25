package api.application;

import model.ElevatorState;

/**
 * Listener that is notified whenever a new elevator state is available.
 */
@FunctionalInterface
public interface StateListener {

    /**
     * Called when the elevator state has changed or a new state was received.
     *
     * @param elevatorState the latest elevator state
     */
    void stateUpdated(ElevatorState elevatorState);
}
