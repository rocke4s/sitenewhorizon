package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.config.BasicAuthConfig;
import net.proselyte.springsecurityapp.model.Profile;
import net.proselyte.springsecurityapp.model.Tasks;
import net.proselyte.springsecurityapp.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class NewTaskController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    private BasicAuthConfig basicAuthConfig = new BasicAuthConfig();
    @RequestMapping(value = "/new_task", method = RequestMethod.GET)
    public String createTask(Tasks newTask, Model model) {
        List<String> listImportance = new ArrayList<>();
        listImportance.add("Высокая");
        listImportance.add("Средняя");
        listImportance.add("Низкая");
        model.addAttribute("newTask", newTask);
        model.addAttribute("listImportance", listImportance);
        return "new_task";
    }

    @RequestMapping(value = "/new_tasker", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String postNewTask(@ModelAttribute("newTask") Tasks newTask, Authentication authentication, Model model) throws IOException {
        HttpGet request = null;
        switch (newTask.getTaskImportance()) {
            case "Высокая":
                newTask.setTaskImportance("2");
                break;
            case "Средняя":
                newTask.setTaskImportance("1");
                break;
            case "Низкая":
                newTask.setTaskImportance("0");
                break;
            default:
                break;
        }
        List<String> fileName = new ArrayList<>();
        int x = 0;
        if (newTask.getFile().length > 0) {
            for (MultipartFile file : newTask.getFile()) {
                fileName.add(StringUtils.cleanPath(file.getOriginalFilename()));
                try {
                    File directory = new File("data/" + newTask.getNameTask());
                    directory.mkdir();
                    Path path = Paths.get("data/" + newTask.getNameTask() + "/" + fileName.get(x));
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x++;
            }
        }
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        newTask.setNameTask(newTask.getNameTask().replaceAll("\\s+", "%20"));
        newTask.setTaskContent(newTask.getTaskContent().replaceAll("\\s+", "%20"));
        x = 0;
        String q = "";
        for (MultipartFile file : newTask.getFile()) {
            if (file.getSize() != 0) {
                fileName.set(x, fileName.get(x).replaceAll("\\s+", "%20"));
                q += "http://" + basicAuthConfig.getIp2() + "/data/" + newTask.getNameTask() + "/" + fileName.get(x) + "\'";
                x++;
            }
        }
        try {
            if (!newTask.getNameTask().equals("") && !newTask.getTaskContent().equals("")) {
                if (q != "") {
                    request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetCreateTask/" + prof.getUidUser() + "/"
                            + newTask.getNameTask() + "/" + newTask.getTaskContent() + "/"
                            + newTask.getTaskImportance() + "?File=" + URLEncoder.encode(q, StandardCharsets.UTF_8.toString()));
                } else {
                    request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetCreateTask/" + prof.getUidUser() + "/"
                            + newTask.getNameTask() + "/" + newTask.getTaskContent() + "/" + newTask.getTaskImportance());
                }
                CloseableHttpClient client = HttpClientBuilder.create().build();
                String encoding = Base64.getEncoder().encodeToString((basicAuthConfig.forBasicAuth()[0] + ":" + basicAuthConfig.forBasicAuth()[1]).getBytes());
                String result;
                request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
                CloseableHttpResponse response = client.execute(request);//выполняем запрос
                HttpEntity entity = response.getEntity();//получаем ответ от АПИ
                result = EntityUtils.toString(entity);//засовываем ответ в строку
                EntityUtils.consume(entity);//ответ парсим и кидаем в бд уид и все остальные введенные данные
                //  userForm.setUidUser();
                response.close();
                return "welcome";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        List<String> listImportance = new ArrayList<>();
        listImportance.add("Высокая");
        listImportance.add("Средняя");
        listImportance.add("Низкая");
        model.addAttribute("newTask", newTask);
        model.addAttribute("listImportance", listImportance);
        model.addAttribute("error_null", "Заполните пустые поля!");
        return "new_task";
    }
}
