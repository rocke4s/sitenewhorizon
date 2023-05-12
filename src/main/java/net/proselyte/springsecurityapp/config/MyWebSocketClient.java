package net.proselyte.springsecurityapp.config;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient extends WebSocketClient {

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // WebSocket соединение установлено
        System.out.println("WebSocket соединение установлено:");
    }

    @Override
    public void onMessage(String message) {
        // Получено сообщение от WebSocket сервера
        System.out.println("Получено сообщение от сервера: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // WebSocket соединение закрыто
        System.out.println("WebSocket соединение закрыто.");
    }

    @Override
    public void onError(Exception ex) {
        // Ошибка при работе с WebSocket соединением
        System.err.println("Ошибка при работе с WebSocket соединением: " + ex);
    }
}