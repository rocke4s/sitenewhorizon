package net.proselyte.springsecurityapp.chat_server;

import com.google.gson.Gson;
import net.proselyte.springsecurityapp.model.Message;
import net.proselyte.springsecurityapp.model.Until;





import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
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
        Until message2 = new Until();
        message1 = g.fromJson(message, Message.class);
            if(message1.getMessage()==null) {
                System.out.println("Пришло сообщение не для чата!");
                message1 = null;
            }
        message2 = g.fromJson(message, Until.class);
        if(message2.getUntil()==null) {
            System.out.println("Пришло сообщение для чата!");
            message2 = null;
        }

        if(message1!=null)
        {
            for (Map.Entry<Session, String> entry : clients.entrySet()) {
                if(entry.getValue().equals("") || !entry.getValue().equals(message1.getNumberTask()))
                {
                    entry.setValue(message1.getNumberTask());
                }
            }
            for (Map.Entry<Session, String> entry : clients.entrySet()) {
                if (entry.getValue().equals(message1.getNumberTask())) {
                    entry.getKey().getBasicRemote().sendText("{ \"NumberTask\":\""+message1.getNumberTask()+"\",\"Name\":\""+message1.getName()
                            +"\",\"message\":\""+message1.getMessage()+"\" }");
                }
            }
        }
        if(message2!=null)
        {
            for (Map.Entry<Session, String> entry : clients.entrySet()) {
                if(entry.getValue().equals("") || !entry.getValue().equals(message2.getNumberTask()))
                {
                    entry.setValue(message2.getNumberTask());
                }
            }
            for (Map.Entry<Session, String> entry : clients.entrySet()) {
                if (entry.getValue().equals(message2.getNumberTask())) {
                    entry.getKey().getBasicRemote().sendText("{ \"NumberTask\":\""+message2.getNumberTask()+"\",\"NameTask\":\""+message2.getNameTask()
                            +"\",\"Until\":\""+message2.getUntil()+"\",\"ChangeType\":\""+message2.getChangeType()+"\"," +
                            "\"Time\":\""+message2.getTime()+"\" }");
                }
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