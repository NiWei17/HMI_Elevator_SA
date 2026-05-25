package gui;

import api.application.CommandService;
import javafx.fxml.FXML;

/**
 * Handles user interactions from the main window.
 *
 * This class is connected to the FXML file and processes
 * button events triggered by the user.
 */
public class MainWindowEventHandler {

    private CommandService commandService;

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
}
