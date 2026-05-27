package api.basic;

import api.application.WebSocketHandler;
import api.application.WebSocketSender;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * WebSocket client for communication with the elevator control system.
 *
 * This class extends the standard WebSocket client library and delegates
 * all events to a {@link WebSocketHandler} for processing. It serves
 * as an adapter between the WebSocket protocol and the application's
 * message handling system.
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient
        implements WebSocketSender {

    /** Handler for WebSocket events and messages */
    private final WebSocketHandler handler;

    /**
     * Creates a new WebSocket client instance.
     *
     * @param serverUri the URI of the WebSocket server to connect to
     * @param handler the handler for processing WebSocket events
     */
    public WebSocketClient(URI serverUri, WebSocketHandler handler) {
        super(serverUri);
        this.handler = handler;
    }

    /**
     * Called when the WebSocket connection is established.
     *
     * Delegates to the configured handler.
     *
     * @param handshake the server handshake information
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        handler.onConnected();
    }

    /**
     * Sends a message through the WebSocket connection.
     *
     * @param message the message to be sent to the server
     */
    @Override
    public void sendMessage(String message) {
        send(message);
    }

    /**
     * Called when a message is received from the WebSocket server.
     *
     * Delegates message processing to the configured handler.
     *
     * @param message the message received from the server
     */
    @Override
    public void onMessage(String message) {
        handler.onMessage(message);
    }

    /**
     * Called when the WebSocket connection is closed.
     *
     * Delegates disconnection handling to the configured handler.
     *
     * @param code the WebSocket close code
     * @param reason the reason for closing the connection
     * @param remote true if the closing was initiated by the remote server, false if by the client
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        handler.onDisconnected(code, reason, remote);
    }

    /**
     * Called when an error occurs on the WebSocket connection.
     *
     * Delegates error handling to the configured handler.
     *
     * @param ex the exception that occurred
     */
    @Override
    public void onError(Exception ex) {
        handler.onError(ex.getMessage());
    }
}
