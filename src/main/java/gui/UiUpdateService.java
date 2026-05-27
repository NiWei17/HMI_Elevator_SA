package gui;

import api.application.StateListener;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.ElevatorState;

/**
 * Service responsible for updating the JavaFX user interface
 * with the latest elevator state.
 *
 * <p>This service is notified by the {@link api.application.StateService}
 * whenever a new {@link ElevatorState} is received. UI updates are
 * executed on the JavaFX Application Thread.</p>
 */
public class UiUpdateService implements StateListener {

    private static final Color ACTIVE_COLOR = Color.LIMEGREEN;
    private static final Color INACTIVE_COLOR = Color.RED;
    private static final Color ERROR_COLOR = Color.ORANGE;

    private final Circle doorStateOpened;
    private final Circle doorStateClosed;
    private final Circle motorStateReady;
    private final Circle motorStateOn;

    /**
     * Creates a new UI update service for the given status indicators.
     *
     * @param doorStateOpened UI indicator showing whether the elevator door is opened
     * @param doorStateClosed UI indicator showing whether the elevator door is closed
     * @param motorStateReady UI indicator showing whether the motor is ready
     * @param motorStateOn UI indicator showing whether the motor is currently running
     */
    public UiUpdateService(
            Circle doorStateOpened,
            Circle doorStateClosed,
            Circle motorStateReady,
            Circle motorStateOn
    ) {
        this.doorStateOpened = doorStateOpened;
        this.doorStateClosed = doorStateClosed;
        this.motorStateReady = motorStateReady;
        this.motorStateOn = motorStateOn;
    }

    @Override
    public void stateUpdated(ElevatorState elevatorState) {
        Platform.runLater(() -> applyState(elevatorState));
    }

    /**
     * Applies the given elevator state to the UI indicators.
     *
     * <p>The door indicators are updated according to the door status.
     * If a motor error is active, both motor indicators are shown using
     * the configured error color. Otherwise, the motor indicators are
     * updated according to the motor status.</p>
     *
     * @param state the elevator state to display in the UI
     */
    private void applyState(ElevatorState state) {
        setLamp(doorStateOpened, state.isDoorOpened());
        setLamp(doorStateClosed, state.isDoorClosed());

        if (state.isMotorError()) {
            motorStateReady.setFill(ERROR_COLOR);
            motorStateOn.setFill(ERROR_COLOR);
            return;
        }

        setLamp(motorStateReady, state.isMotorReady());
        setLamp(motorStateOn, state.isMotorOn());
    }

    /**
     * Updates a single status indicator.
     *
     * @param circle the circle indicator to update
     * @param active true if the indicator should be displayed as active,
     *               false if it should be displayed as inactive
     */
    private void setLamp(Circle circle, boolean active) {
        circle.setFill(active ? ACTIVE_COLOR : INACTIVE_COLOR);
    }
}