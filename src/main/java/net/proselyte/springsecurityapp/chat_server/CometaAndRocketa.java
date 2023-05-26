package net.proselyte.springsecurityapp.chat_server;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


    @WebServlet("/chat")
    public class CometaAndRocketa extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String data = getDataFromSomeSource(); // Получаем данные с какого-то источника
            while (data == null || data.isEmpty()) {  // Цикл ожидания, пока не появятся новые данные
                try {
                    Thread.sleep(1000); // Приостанавливаем поток на 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                data = getDataFromSomeSource();
            }
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(data); // Отправляем ответ клиенту
        }

        private String getDataFromSomeSource() {
            // Здесь должен быть код получения новых данных
            return "New data: " + new Date();
        }
    }

