package api.application;

import model.ElevatorState;
import application.JsonMessageParser;


public class WebSocketHandler {

    private ElevatorState elevatorState;

    public void onConnected() {
        System.out.println("Connected to Control Server.");
    }

    public void onDisconnected(int code, String reason, boolean remote) {
        System.out.println("Disconnected: Code=" + code + ", Reason=" + reason);
    }

    public void onMessage(String rawJson) {
        try {
            elevatorState = JsonMessageParser.parseState(rawJson);
        } catch (Exception e) {
            onError(e.getMessage());
        }
    }

    public void onError(String exMessage) {
        System.out.println("WebSocket error: " + exMessage);
    }
}
