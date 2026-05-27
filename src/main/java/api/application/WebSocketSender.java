package api.application;

/**
 * Abstraction for sending messages to the elevator control system.
 *
 * Application services use this interface instead of depending on the
 * concrete WebSocket implementation.
 */
public interface WebSocketSender {

    /**
     * Sends a message to the elevator control system.
     *
     * @param message the message to send
     */
    void sendMessage(String message);
}
