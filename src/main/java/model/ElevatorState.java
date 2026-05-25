package model;

/**
 * Data model representing the current state of the elevator.
 *
 * This class stores status information received from
 * the control system, including door state, motor state,
 * error conditions, and cycle information.
 */
public class ElevatorState {

    private final boolean doorOpened;
    private final boolean doorClosed;
    private final boolean motorReady;
    private final boolean motorOn;
    private final boolean motorError;
    private final int cycles;
    private final int id;

    /**
     * Creates a new ElevatorState with the given status values.
     *
     * @param doorOpened indicates whether the elevator door is opened
     * @param doorClosed indicates whether the elevator door is closed
     * @param motorReady indicates whether the motor is ready for operation
     * @param motorOn indicates whether the motor is currently running
     * @param motorError indicates whether an error state is active for the motor
     * @param cycles the number of operational cycles completed
     * @param id the unique identifier for this elevator or status message
     */
    public ElevatorState(
            boolean doorOpened,
            boolean doorClosed,
            boolean motorReady,
            boolean motorOn,
            boolean motorError,
            int cycles,
            int id
    ) {
        this.doorOpened = doorOpened;
        this.doorClosed = doorClosed;
        this.motorReady = motorReady;
        this.motorOn = motorOn;
        this.motorError = motorError;
        this.cycles = cycles;
        this.id = id;
    }

    /**
     * Returns whether the elevator door is currently opened.
     *
     * @return true if the door is opened, false otherwise
     */
    public boolean isDoorOpened() {
        return doorOpened;
    }

    /**
     * Returns whether the elevator door is currently closed.
     *
     * @return true if the door is closed, false otherwise
     */
    public boolean isDoorClosed() {
        return doorClosed;
    }

    /**
     * Returns whether the motor is ready for operation.
     *
     * @return true if the motor is ready, false otherwise
     */
    public boolean isMotorReady() {
        return motorReady;
    }

    /**
     * Returns whether the motor is currently running.
     *
     * @return true if the motor is on, false otherwise
     */
    public boolean isMotorOn() {
        return motorOn;
    }

    /**
     * Returns whether the motor is in an error state.
     *
     * @return true if a motor error is active, false otherwise
     */
    public boolean isMotorError() {
        return motorError;
    }

    /**
     * Returns the number of operational cycles completed.
     *
     * @return the cycle count
     */
    public int getCycles() {
        return cycles;
    }

    /**
     * Returns the unique identifier for this elevator or status message.
     *
     * @return the elevator ID
     */
    public int getId() {
        return id;
    }
}
