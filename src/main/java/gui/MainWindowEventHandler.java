package gui;

import api.application.CommandService;
import javafx.fxml.FXML;
import api.application.StateService;
import javafx.scene.shape.Circle;

/**
 * Handles user interactions from the main window.
 *
 * This class is connected to the FXML file and processes
 * button events triggered by the user.
 */
public class MainWindowEventHandler {

    private CommandService commandService;

    @FXML private Circle doorStateOpened;
    @FXML private Circle doorStateClosed;
    @FXML private Circle motorStateReady;
    @FXML private Circle motorStateOn;

    /**
     * Sets the service used to send commands to the elevator control system.
     *
     * @param commandService the command service used for elevator commands
     */
    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Called when the Open Door button is clicked.
     */
    @FXML
    private void onOpenDoorClicked() {
        if (commandService != null) {
            commandService.openDoor();
        }
    }

    /**
     * Called when the Close Door button is clicked.
     */
    @FXML
    private void onCloseDoorClicked() {
        if (commandService != null) {
            commandService.closeDoor();
        }
    }

    /**
     * Called when the Reset button is clicked.
     */
    @FXML
    private void onResetClicked() {
        if (commandService != null) {
            commandService.reset();
        }
    }

     /**
     * Sets the service used to receive elevator state updates.
     *
     * @param stateService the state service used for receiving elevator states
     */
    public void setStateService(StateService stateService) {
        UiUpdateService uiUpdateService = new UiUpdateService(
                doorStateOpened,
                doorStateClosed,
                motorStateReady,
                motorStateOn
        );

        stateService.setStateListener(uiUpdateService);
    }
}
