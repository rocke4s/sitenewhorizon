package net.proselyte.springsecurityapp.config;


import com.google.gson.Gson;
import net.proselyte.springsecurityapp.model.Chat;
import net.proselyte.springsecurityapp.model.Profile;
import net.proselyte.springsecurityapp.model.Task;
import net.proselyte.springsecurityapp.model.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

@ServerEndpoint("/chat")
public class WebSocketConfig {

    @OnOpen
    public void onOpen(Session session) {

        System.out.println("Connected to client: " + session.getId());
    }
    public String[] forBasicAuth() throws IOException{
        Properties props = new Properties();
        try (
            InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
        }
        String username = props.getProperty("username");//Переменные для
        String password = props.getProperty("password");//basic auth
        String[] str = new String[]{username,password};
        return str;
    }
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
//        HttpGet request = new HttpGet("http://192.168.1.224/franrit/hs/RitExchange/getChat/");
//        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
//        CloseableHttpResponse response = client.execute(request);//выполняем запрос
//        Task task = new Task();
//        Chat chat = new Chat();
//        Profile profile = new Profile();
//        try {
//            HttpEntity entity = response.getEntity();//получаем ответ от АПИ
//            String result = EntityUtils.toString(entity);//засовываем ответ в строку
//            EntityUtils.consume(entity);
//            Gson g = new Gson();
//            String str = result;
//            task = g.fromJson(str, Task.class);
//            //  System.out.println(profile.getUidUser());
//
//        } finally {
//            response.close();
//        }
        try {
            if(message!=null && !message.equals("") && message.indexOf("message:")!=-1) {
                String masage =message.substring(message.indexOf("message:")+8,message.indexOf(",uidDoc:"));
                session.getBasicRemote().sendText("User: " + masage);
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Disconnected from client: " + session.getId());
    }
}

