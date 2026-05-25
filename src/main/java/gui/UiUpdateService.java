package gui;

import api.application.StateService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.ElevatorState;

/**
 * Service responsible for updating the JavaFX user interface
 * with the latest elevator state.
 *
 * <p>The service periodically reads the current {@link ElevatorState}
 * from the {@link StateService} and updates the status indicators
 * displayed in the UI. All UI changes are executed on the JavaFX
 * Application Thread.</p>
 */
public class UiUpdateService {

    /**
     * Interval in which the UI is refreshed.
     */
    private static final Duration UI_UPDATE_INTERVAL = Duration.millis(200);

    /**
     * Color used for active status indicators.
     */
    private static final Color ACTIVE_COLOR = Color.LIMEGREEN;

    /**
     * Color used for inactive status indicators.
     */
    private static final Color INACTIVE_COLOR = Color.RED;

    /**
     * Color used to indicate a motor error state.
     */
    private static final Color ERROR_COLOR = Color.ORANGE;

    /**
     * Service that provides the latest elevator state.
     */
    private final StateService stateService;

    /**
     * UI indicator showing whether the door is opened.
     */
    private final Circle doorStateOpened;

    /**
     * UI indicator showing whether the door is closed.
     */
    private final Circle doorStateClosed;

    /**
     * UI indicator showing whether the motor is ready.
     */
    private final Circle motorStateReady;

    /**
     * UI indicator showing whether the motor is currently running.
     */
    private final Circle motorStateOn;

    /**
     * Timeline used to periodically trigger UI updates.
     */
    private final Timeline updateTimeline;

    /**
     * Creates a new UIUpdateService.
     *
     * @param stateService service providing the current elevator state
     * @param doorStateOpened UI indicator for the opened door state
     * @param doorStateClosed UI indicator for the closed door state
     * @param motorStateReady UI indicator for the motor ready state
     * @param motorStateOn UI indicator for the motor running state
     */
    public UIUpdateService(
            StateService stateService,
            Circle doorStateOpened,
            Circle doorStateClosed,
            Circle motorStateReady,
            Circle motorStateOn
    ) {
        this.stateService = stateService;
        this.doorStateOpened = doorStateOpened;
        this.doorStateClosed = doorStateClosed;
        this.motorStateReady = motorStateReady;
        this.motorStateOn = motorStateOn;

        this.updateTimeline = new Timeline(
                new KeyFrame(UI_UPDATE_INTERVAL, event -> updateUi())
        );
        this.updateTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the periodic UI update process.
     */
    public void start() {
        updateTimeline.play();
    }

    /**
     * Stops the periodic UI update process.
     */
    public void stop() {
        updateTimeline.stop();
    }

    /**
     * Reads the latest elevator state from the {@link StateService}
     * and schedules the UI update on the JavaFX Application Thread.
     *
     * <p>If no state has been received yet, the UI remains unchanged.</p>
     */
    private void updateUi() {
        ElevatorState state = stateService.getCurrentState();

        if (state == null) {
            return;
        }

        Platform.runLater(() -> applyState(state));
    }

    /**
     * Applies the given elevator state to the UI indicators.
     *
     * <p>Door and motor indicators are updated according to the
     * boolean values contained in the {@link ElevatorState}. If a
     * motor error is active, both motor indicators are shown using
     * the configured error color.</p>
     *
     * @param state the elevator state to display
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
     * @param circle the UI indicator to update
     * @param active true if the indicator should be shown as active,
     *               false otherwise
     */
    private void setLamp(Circle circle, boolean active) {
        circle.setFill(active ? ACTIVE_COLOR : INACTIVE_COLOR);
    }
}