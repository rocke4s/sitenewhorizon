package net.proselyte.springsecurityapp.controller;

import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import net.proselyte.springsecurityapp.config.BasicAuthConfig;
import net.proselyte.springsecurityapp.config.Sender;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.ChangeLogTaskService;
import net.proselyte.springsecurityapp.service.ChatUserService;
import net.proselyte.springsecurityapp.service.ProfileService;
import net.proselyte.springsecurityapp.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
@Controller
public class ChangesController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChangeLogTaskService changeLogTaskService;
    @Autowired
    private ChatUserService chatUserService;
    private BasicAuthConfig basicAuthConfig = new BasicAuthConfig();
    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public void sendMessage(@RequestHeader("NumberTask") String NumberTask, @RequestHeader(value = "userSender", required = false) String userSender,
                            @RequestHeader(value = "userRecipient", required = false) String userRecipient,
                            @RequestHeader("message") String message, @RequestHeader("dataSend") String dataSend,
                            @RequestHeader(value = "userName", required = false) String userName,
                            @RequestHeader(value = "uidDoc", required = false) String uidDoc) throws IOException {
        HttpPost request = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        String encoding = Base64.getEncoder().encodeToString((basicAuthConfig.forBasicAuth()[0] + ":" + basicAuthConfig.forBasicAuth()[1]).getBytes());
        User user = userService.findByUsername(userName);
        ChatUser chatUser = new ChatUser();
        try {
            chatUser.setNumberTask(URLDecoder.decode(NumberTask, "UTF-8"));
            if (userSender != null) {
                request = new HttpPost("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/discussion/" + uidDoc + "/" + user.getUidUser());
                request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
                request.setHeader("Content-Type", "charset=UTF-8");
                StringEntity requestBodyEntity = new StringEntity(URLDecoder.decode(message, "UTF-8"), "UTF-8");
                request.setEntity(requestBodyEntity);
                HttpResponse httpResponse = httpClient.execute(request);
                chatUser.setUserSenders(URLDecoder.decode(userSender, "UTF-8"));
                chatUser.setMessage(URLDecoder.decode(message, "UTF-8"));
            }
            if (userRecipient != null) {
                chatUser.setUserRecipient(URLDecoder.decode(basicAuthConfig.decodRequest(userRecipient), "UTF-8"));
                chatUser.setMessage(URLDecoder.decode(basicAuthConfig.decodRequest(message), "UTF-8"));
            }
            chatUser.setDateSend(URLDecoder.decode(dataSend, "UTF-8"));
            chatUser.setIsNewMessage("new");
        } catch (Exception e) {
            System.out.println(e);
        }
        // сохраняем сообщение в базе данных
        chatUserService.save(chatUser);
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatUser> getMessages() {
        List<ChatUser> listChatUser = chatUserService.findByisNewMessage("new");
        for (ChatUser chuser : chatUserService.findByisNewMessage("new")) {
            chuser.setIsNewMessage("wait");
            chatUserService.save(chuser);
        }
        // получаем все сообщения из базы данных
        return listChatUser;
    }

    @RequestMapping(value = "/clientall", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatUser> getAllMessages() {
        List<ChatUser> listChatUser1 = chatUserService.findByisNewMessage("old");
        listChatUser1.addAll(chatUserService.findByisNewMessage("wait"));
        return listChatUser1;
    }

    @RequestMapping(value = "/newchanges", method = RequestMethod.GET)
    @ResponseBody
    public List<ChangeLogTask> getNewChanges() {
        List<ChangeLogTask> changeLogTask = changeLogTaskService.findByisNewChanges("new");
//        for(ChangeLogTask chtask: changeLogTaskService.findByisNewChanges("new"))
//        {
//            changeLogTaskService.save(chtask);
//        }
        // получаем все сообщения из базы данных
        return changeLogTask;
    }

    @RequestMapping(value = "/changesall", method = RequestMethod.GET)
    @ResponseBody
    public List<ChangeLogTask> getAllMessages(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<ChangeLogTask> changeLogTask = new ArrayList<>();
        try {
            changeLogTask = changeLogTaskService.findByUidUser(user.getUidUser());
        } catch (Exception e) {
            System.out.println(e);
        }
        return changeLogTaskService.findByUidUser(user.getUidUser());
    }

    @RequestMapping(value = "/stopred", method = RequestMethod.GET)
    public void stopRed(@RequestHeader("NumberTask") String NumberTask, @RequestHeader("Type") String Type) {
        try {
            if (Type.equals("stats")) {
                changeLogTaskService.updateStatusByNumberTask("old", NumberTask);
            }
            if (Type.equals("chat")) {
                chatUserService.updateChatByNumberDoc("old", NumberTask);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/statuser", method = RequestMethod.GET)
    public void doGets(HttpServletRequest request) throws IOException {
        try {
            // Получаем данные из GET запроса

            String NameTask = basicAuthConfig.decodRequest(request.getHeader("NameTask"));
            String NumberTask = basicAuthConfig.decodRequest(request.getHeader("NumberTask"));
            String OldStatus = basicAuthConfig.decodRequest(request.getHeader("OldStatus"));
            String NewStatus = basicAuthConfig.decodRequest(request.getHeader("NewStatus"));
            String UserName = basicAuthConfig.decodRequest(request.getHeader("UserName"));

            Sender sender1 = new Sender();
            User user = userService.findByUsername(UserName);
            Profile prof = profileService.findByUidUser(user.getUidUser());
            try {
                sender1.send(prof.getUserMail(), "Изменение статуса заявки", "Статус заявки '" + NameTask + "' №" + NumberTask + " изменился с "
                        + OldStatus + " на " + NewStatus);
                System.out.println("Email sent successfully");
            } catch (jakarta.mail.MessagingException e) {
                System.err.println("Email sending failed: " + e.getMessage());
            }
            ChangeLogTask changeLogTask = new ChangeLogTask();
            changeLogTask.setNumberTask(NumberTask);
            changeLogTask.setChangetype("Изменение статуса");
            changeLogTask.setChange("Статус изменен с " + OldStatus + " на " + NewStatus);
            changeLogTask.setNameTask(NameTask);
            Calendar currentCalendar = Calendar.getInstance();
            changeLogTask.setTime(currentCalendar.getTime().toString());
            changeLogTask.setUidUser(user.getUidUser());
            changeLogTask.setIsNewChanges("new");
            changeLogTaskService.save(changeLogTask);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/untiluser", method = RequestMethod.GET)
    public void doUntil(HttpServletRequest request, Authentication authentication) {
        try {
            String NumberTask = basicAuthConfig.decodRequest(request.getHeader("NumberTask"));
            String NameTask = basicAuthConfig.decodRequest(request.getHeader("NameTask"));
            String OldUntil = basicAuthConfig.decodRequest(request.getHeader("OldUntil"));
            String NewUntil = basicAuthConfig.decodRequest(request.getHeader("NewUntil"));
            String Login = basicAuthConfig.decodRequest(request.getHeader("UserName"));
            User user = userService.findByUsername(Login);
            ChangeLogTask changeLogTask = new ChangeLogTask();
            changeLogTask.setChangetype("Изменение срока");
            changeLogTask.setChange("Срок изменен с " + OldUntil + " на " + NewUntil);
            changeLogTask.setNameTask(NameTask);
            changeLogTask.setNumberTask(NumberTask);
            Calendar currentCalendar = Calendar.getInstance();
            changeLogTask.setTime(currentCalendar.getTime().toString());
            changeLogTask.setUidUser(user.getUidUser());
            changeLogTask.setIsNewChanges("new");
            changeLogTaskService.save(changeLogTask);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @RequestMapping(value = "/change_status", method = RequestMethod.GET)
    public String changeStatus(@ModelAttribute("changeStatus") ChangeStatus changeStatus, Authentication authentication,
                               String uidDoc_8, String uidDoc_5, String uidDoc_0) throws IOException, MessagingException {
        HttpGet request = null;
        String doc = "";
        String stateDoc = "";
        if (uidDoc_5 != null && !uidDoc_5.isEmpty())//передаем выбранное состояние заявки - "на доработку"
        {
            changeStatus.setCauseChangeStatus(changeStatus.getCauseChangeStatus().replaceAll("\\s+", "%20"));
            request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetTestResult/" + uidDoc_5 + "/5?Reason=" + changeStatus.getCauseChangeStatus());
            doc = uidDoc_5;
            stateDoc = "На доработке";
        }
        if (uidDoc_8 != null && !uidDoc_8.isEmpty())//передаем выбранное состояние заявки - "выполнено"
        {
            request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetTestResult/" + uidDoc_8 + "/8");
            doc = uidDoc_8;
            stateDoc = "Проверено";
        }
        if (uidDoc_0 != null && !uidDoc_0.isEmpty())//передаем выбранное состояние заявки - "отмена"
        {
            request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetTestResult/" + uidDoc_0 + "/3");
            doc = uidDoc_0;
            stateDoc = "Отменено";
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String encoding = Base64.getEncoder().encodeToString((basicAuthConfig.forBasicAuth()[0] + ":" + basicAuthConfig.forBasicAuth()[1]).getBytes());
        String result;
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
        CloseableHttpResponse response = client.execute(request);//выполняем запрос
        try {
            HttpEntity entity = response.getEntity();//получаем ответ от АПИ
            result = EntityUtils.toString(entity);//засовываем ответ в строку
            EntityUtils.consume(entity);//ответ парсим и кидаем в бд уид и все остальные введенные данные
            System.out.println(result);
            Gson g = new Gson();
            //  userForm.setUidUser();
        } finally {
            response.close();
        }

        return "redirect:/tasks";
    }
}