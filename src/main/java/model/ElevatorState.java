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

    public boolean isDoorOpened() {
        return doorOpened;
    }

    public boolean isDoorClosed() {
        return doorClosed;
    }

    public boolean isMotorReady() {
        return motorReady;
    }

    public boolean isMotorOn() {
        return motorOn;
    }

    public boolean isMotorError() {
        return motorError;
    }

    public int getCycles() {
        return cycles;
    }

    public int getId() {
        return id;
    }
}