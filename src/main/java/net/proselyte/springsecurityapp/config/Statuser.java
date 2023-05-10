package net.proselyte.springsecurityapp.config;

import com.google.gson.Gson;
import net.proselyte.springsecurityapp.model.Statuska;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
@ServerEndpoint("/statuser")
public class Statuser extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        System.out.println(request.getHeader("dota"));
        String line;
        line = reader.readLine();
        Gson g = new Gson();
        Statuska task = g.fromJson(line, Statuska.class);
        System.out.println(task.toString());
    }
}
