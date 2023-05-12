package net.proselyte.springsecurityapp.chat_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint("/chat")
public class WebSocketServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/chat", new ChatHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("WebSocket Server is running on port 8081");
    }

    static class ChatHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            if (he.getRequestMethod().equalsIgnoreCase("GET")) {
                Headers headers = he.getResponseHeaders();
                headers.add("Content-Type", "text/html; charset=utf-8");
                String response = "WebSocket Server is running on port 8081. You can connect to /chat WebSocket endpoint.";
                he.sendResponseHeaders(200, response.length());
                he.getResponseBody().write(response.getBytes());
                he.getResponseBody().close();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("WebSocket message: " + message);
        session.getBasicRemote().sendText("Response to " + message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket closed: " + session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("WebSocket error: " + t.getMessage());
    }
}
