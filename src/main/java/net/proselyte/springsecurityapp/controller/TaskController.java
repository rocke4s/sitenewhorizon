package net.proselyte.springsecurityapp.controller;

import com.google.gson.Gson;
import net.proselyte.springsecurityapp.config.BasicAuthConfig;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.ProfileService;
import net.proselyte.springsecurityapp.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
@Controller
public class TaskController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    private BasicAuthConfig basicAuthConfig = new BasicAuthConfig();
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ModelAndView tasks(Model model, Authentication authentication, ChangeStatus changeStatus) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        String encoding = Base64.getEncoder().encodeToString((basicAuthConfig.forBasicAuth()[0] + ":" + basicAuthConfig.forBasicAuth()[1]).getBytes());
        HttpGet request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/getDocuments/" + prof.getUidOrg() + "/" + prof.getUidUser() + "/0");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
        CloseableHttpResponse response = null;
        Task task = new Task();
        Chat chat = new Chat();
        Profile profile = new Profile();
        try {
            response = client.execute(request);//выполняем запрос
            HttpEntity entity = response.getEntity();//получаем ответ от АПИ
            String result = EntityUtils.toString(entity);//засовываем ответ в строку
            EntityUtils.consume(entity);
            Gson g = new Gson();
            String str = result;
            str = str.replaceAll("\"Заявки\"", "\"Tasks\"");
            str = str.replaceAll("Ссылка", "TaskUrl");
            str = str.replaceAll("Контрагент", "TaskPartner");
            str = str.replaceAll("\"СостояниеЗаявки\"", "\"TaskStatus\"");
            str = str.replaceAll("ТипЗадания", "TypeTask");
            str = str.replaceAll("Важность", "TaskImportance");
            str = str.replaceAll("\"Содержание\"", "\"TaskContent\"");
            str = str.replaceAll("Наименование", "NameTask");
            str = str.replaceAll("СрокДо", "TaskDeadline");
            str = str.replaceAll("Трудоемкость", "TaskIntensity");
            str = str.replaceAll("ID", "TaskId");
            str = str.replaceAll("ДатаВыполнено", "TaskDataDone");
            str = str.replaceAll("\"СодержаниеЛУВР\"", "\"TaskContentLVR\"");
            str = str.replaceAll("Номер", "TaskNumber");
            str = str.replaceAll("Дата", "TaskData");
            str = str.replaceAll("\"Отдел\"", "TaskDepartment");
            str = str.replaceAll("http://(?!['\"])[^'\"]+", "<a href='$0'>$0</a><br>");
            str = str.replaceAll("Автор","TaskAuthor");
            task = g.fromJson(str, Task.class);
            Collections.sort(task.getTasks(), Comparator.comparing(Tasks::getTaskNumber).reversed());
        } catch (Exception ex) {
            ModelAndView modelAndView = new ModelAndView("error-page");
            response.close();
            return modelAndView;
        }

//        List<ChangeLogTask> changeLogTask = new ArrayList<>();
//        try {
//            changeLogTask = changeLogTaskService.findByUidUser(user.getUidUser());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        response.close();
        ModelAndView modelAndView = new ModelAndView("tasks");
        modelAndView.addObject("Tasks", task);
        modelAndView.addObject("changeStatus", changeStatus);
        modelAndView.addObject("chat", chat);
        modelAndView.addObject("Profile", prof);
//        modelAndView.addObject("changeLogTask",changeLogTask);
        return modelAndView;
    }
}
