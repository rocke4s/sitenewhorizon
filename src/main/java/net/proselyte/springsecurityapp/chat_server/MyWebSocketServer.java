package net.proselyte.springsecurityapp.chat_server;

import com.google.gson.Gson;
import net.proselyte.springsecurityapp.model.Message;
import net.proselyte.springsecurityapp.model.Task;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyWebSocketServer extends WebSocketServer {
//    private List<String> xoxo=new ArrayList<>();
//    List<Session> clients = new ArrayList<>();
//
//    public MyWebSocketServer(InetSocketAddress address) {
//        super(address);
//    }
//
//    @Override
//    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake,Session session) {
//        System.out.println("WebSocket opened: " + webSocket.getRemoteSocketAddress());
//        clients.add(session);
//    }
//
//    @Override
//    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
//        System.out.println("WebSocket closed: " + webSocket.getRemoteSocketAddress());
//    }
//
//    @Override
//    public void onMessage(WebSocket webSocket, String s) {
//        System.out.println("WebSocket message received: " + s);
//        xoxo.add(s);
//            String str="";
//            for(int x=0;x<xoxo.size();x++){
//                 str+="\n"+xoxo.get(x);
//            }
//            webSocket.send(str);
//    }
//
//    @Override
//    public void onError(WebSocket webSocket, Exception e) {
//        System.out.println("WebSocket error: " + e.getMessage());
//    }
//
//    @Override
//    public void onStart() {
//
//    }
//
//    public static void main(String[] args) {
//        MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress(8082));
//        server.start();
//    }
//}




import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/chat")
public class MyWebSocketServer  {

    private static Map<Session,String> clients = new HashMap<>();
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected: " + session.getId());
        clients.put(session, "");
    }

    @OnMessage
    public void onMessage(String message, Session sender) throws IOException {
        System.out.println("Received message from " + sender.getId() + ": " + message);
        Gson g = new Gson();
        Message message1 = new Message();
        message1 = g.fromJson(message, Message.class);
//        for (Session client : clients) {
//            if (!client.equals(sender)) {
//                client.getBasicRemote().sendText(sender.getId());
//            }
//        }
        for (Map.Entry<Session, String> entry : clients.entrySet()) {
            if(entry.getValue().equals("") || !entry.getValue().equals(message1.getUidDoc()))
            {
                entry.setValue(message1.getUidDoc());
            }
        }
        for (Map.Entry<Session, String> entry : clients.entrySet()) {
            if (entry.getValue().equals(message1.getUidDoc())) {
                entry.getKey().getBasicRemote().sendText("{ \"Docid\":\""+message1.getUidDoc()+"\",\"Named\":\""+message1.getName()
                +"\",\"messg\":\""+message1.getMessage()+"\" }");
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Client disconnected: " + session.getId());
        clients.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on session " + session.getId());
        throwable.printStackTrace();
    }
}