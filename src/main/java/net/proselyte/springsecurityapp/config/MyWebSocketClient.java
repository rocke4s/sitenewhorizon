package net.proselyte.springsecurityapp.config;

import org.java_websocket.client.WebSocketClient;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class MyWebSocketClient {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Disconnected from server: " + closeReason.getReasonPhrase());
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void main(String[] args) throws Exception {

    }
}