package net.proselyte.springsecurityapp.controller;

import com.google.gson.Gson;
import net.proselyte.springsecurityapp.config.Sender;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.ProfileService;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserValidator userValidator;
    private String ip="192.168.1.224";

    public UserController() throws IOException {
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserReg());

        return "registration";
    }

    public String[] forBasicAuth() throws IOException{
        Properties props = new Properties();
        try (
                InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
        }
        String username = props.getProperty("username");//Переменные для
        String password = props.getProperty("password");//basic auth
        String mailName = props.getProperty("mail.username");
        String mailPass = props.getProperty("mail.password");
        String[] str = new String[]{username,password,mailName,mailPass};
        return str;
    }
    @RequestMapping(value = "/statuser", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String line;
        line = reader.readLine();
        Gson g = new Gson();
        Statuska task = g.fromJson(line, Statuska.class);
        System.out.println(task.toString());
        Sender sender1 = new Sender();
        User user = userService.findByUsername(task.getUserName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        try {
            sender1.send(prof.getUserMail(), "Изменение статуса заявки", "Статус заявки '"+task.getNameTask()+"' №"+task.getNumberTask()+" изменился с "
                    +task.getOldStatus()+" на "+task.getNewStatus());
            System.out.println("Email sent successfully");
        } catch ( jakarta.mail.MessagingException e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserReg userForm, BindingResult bindingResult, Model model) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String redName = userForm.getName().replaceAll("[^\\p{IsCyrillic}]+", "");
        redName = redName.substring(0,1).toUpperCase()+redName.substring(1).toLowerCase();
        String redSurname = userForm.getSurname().replaceAll("[^\\p{IsCyrillic}]+", "");
        redSurname = redSurname.substring(0,1).toUpperCase()+redSurname.substring(1).toLowerCase();
        String redSecondSurname = userForm.getSecondSurname().replaceAll("[^\\p{IsCyrillic}]+", "");
        redSecondSurname = redSecondSurname.substring(0,1).toUpperCase()+redSecondSurname.substring(1).toLowerCase();
        userForm.setName(redName);
        userForm.setSurname(redSurname);
        userForm.setSecondSurname(redSecondSurname);
        String FIO = userForm.getSurname()+"%20"+userForm.getName()+"%20"+userForm.getSecondSurname();
        String test = userForm.getFIO().replaceAll("\\s+","%20");
        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
        HttpGet request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetGUID/"+userForm.getUsername()
                +"/"+userForm.getINN()+"/"+FIO+"/"+userForm.getPhone());
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
        CloseableHttpResponse response = client.execute(request);//выполняем запрос
        Profile profile = new Profile();
        try {
            HttpEntity entity = response.getEntity();//получаем ответ от АПИ
            String result = EntityUtils.toString(entity);//засовываем ответ в строку
            EntityUtils.consume(entity);//ответ парсим и кидаем в бд уид и все остальные введенные данные
            System.out.println(result);
            Gson g = new Gson();
            profile = g.fromJson(result, Profile.class);
            profile.setTelefon(userForm.getPhone());
            profile.setUserMail(userForm.getUserMail());
            System.out.println(profile.getUidUser());
            //  userForm.setUidUser();
        } finally {
            response.close();
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!profile.getUidUser().isEmpty()) {
            userForm.setUidUser(profile.getUidUser());
            User userokForm=null;
            userokForm.setUsername(userForm.getUsername());
            userokForm.setUserMail(userForm.getUserMail());
            userokForm.setINN(userForm.getINN());
            userokForm.setPhone(userForm.getPhone());
            userokForm.setPassword(userForm.getPassword());
            userokForm.setConfirmPassword(userForm.getConfirmPassword());
            userokForm.setId(userForm.getId());
            userokForm.setRoles(userForm.getRoles());

            model.addAttribute("profile",profile);
            userService.save(userokForm);//сохранение в бд
            profileService.save(profile);
        }
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout,User user) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) throws IOException {
        return "welcome";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model, Authentication authentication) {
        System.out.println(authentication.getName());
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        model.addAttribute("Profile",prof);
        return "profile";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ModelAndView tasks(Model model, Authentication authentication,ChangeStatus changeStatus)  throws IOException{
        CloseableHttpClient client = HttpClientBuilder.create().build();
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
        HttpGet request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/getDocuments/"+prof.getUidOrg()+"/"+prof.getUidUser()+"/0");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
        CloseableHttpResponse response = client.execute(request);//выполняем запрос
        Task task = new Task();
        Chat chat = new Chat();
        Profile profile = new Profile();
        try {
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
            str = str.replaceAll("Наименование","NameTask");
            str = str.replaceAll("СрокДо", "TaskDeadline");
            str = str.replaceAll("Трудоемкость", "TaskIntensity");
            str = str.replaceAll("ID", "TaskId");
            str = str.replaceAll("ДатаВыполнено", "TaskDataDone");
            str = str.replaceAll("\"СодержаниеЛУВР\"", "\"TaskContentLVR\"");
            str = str.replaceAll("Номер", "TaskNumber");
            str = str.replaceAll("Дата", "TaskData");
            str = str.replaceAll("Сотрудник", "TaskEmployee");
            task = g.fromJson(str, Task.class);

        } finally {
            response.close();
        }
        ModelAndView modelAndView = new ModelAndView("tasks");
        modelAndView.addObject("Tasks", task);
        modelAndView.addObject("changeStatus",changeStatus);
        modelAndView.addObject("chat",chat);
        return modelAndView;
    }

    @RequestMapping(value = "/change_status", method = RequestMethod.GET)
    public String changeStatus(@ModelAttribute("changeStatus")ChangeStatus changeStatus,Authentication authentication,
                               String uidDoc_8,String uidDoc_5,String uidDoc_0,String TaskNumber) throws IOException {
        HttpGet request = null;
        String doc="";
        String stateDoc="";
        if(uidDoc_5!=null && !uidDoc_5.isEmpty())//передаем выбранное состояние заявки - "на доработку"
        {
            request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetTestResult/"+uidDoc_5+"/5?Reason="+changeStatus.getCauseChangeStatus());
            doc=uidDoc_5;
            stateDoc="На доработке";
        }
        if(uidDoc_8!=null && !uidDoc_8.isEmpty())//передаем выбранное состояние заявки - "выполнено"
        {
            request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetTestResult/"+uidDoc_8+"/8");
            doc=uidDoc_8;
            stateDoc="Проверено";
        }
        if(uidDoc_0!=null && !uidDoc_0.isEmpty())//передаем выбранное состояние заявки - "отмена"
        {
//            request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetTestResult/"+uidDoc_0+"/3");
//            doc=uidDoc_0;
            stateDoc="Отменено";
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
        String result;
//        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
//        CloseableHttpResponse response = client.execute(request);//выполняем запрос
//        try {
//            HttpEntity entity = response.getEntity();//получаем ответ от АПИ
//            result = EntityUtils.toString(entity);//засовываем ответ в строку
//            EntityUtils.consume(entity);//ответ парсим и кидаем в бд уид и все остальные введенные данные
//            System.out.println(result);
//            Gson g = new Gson();
//            //  userForm.setUidUser();
//        } finally {
//            response.close();
//        }



//        tlsSender.send("Изменение статуса заявки", "Документ №"+doc+"\n изменил свое состояние на "+stateDoc, prof.getUserMail());

        return "redirect:/tasks";
    }
    @RequestMapping(value = "/new_task", method = RequestMethod.POST)
    public String createTask(Tasks newTask, Model model) {
        List<String> listImportance = new ArrayList<>();
        listImportance.add("Высокая");
        listImportance.add("Средняя");
        listImportance.add("Низкая");
        model.addAttribute("newTask",newTask);
        model.addAttribute("listImportance",listImportance);
        return "new_task";
    }
    @RequestMapping(value = "/new_tasker", method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String postNewTask(@ModelAttribute("newTask")Tasks newTask,Authentication authentication) throws IOException {
        HttpGet request = null;
        switch (newTask.getTaskImportance())
        {
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
//        File directory = new File("\\\\192.168.1.9\\billi\\"+newTask.getNameTask());
//        directory.mkdir();
//        String fileName = StringUtils.cleanPath(newTask.getFile().getOriginalFilename());
//        try {
//            Path path= Paths.get(directory+"\\"+ fileName);
//            Files.copy(newTask.getFile().getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

       // File directory = new File(newTask.getNameTask());
       // directory.mkdir();
        String fileName = StringUtils.cleanPath(newTask.getFile().getOriginalFilename());
        try {
            Path path= Paths.get("data/"+ fileName);
            Files.copy(newTask.getFile().getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        String q  = newTask.getNameTask()+"\\" + fileName;

        request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetCreateTask/"+ prof.getUidUser()+"/"
                +newTask.getNameTask()+"/"+newTask.getTaskContent()+"/"+newTask.getTaskImportance()+"?File="+ URLEncoder.encode(q, StandardCharsets.UTF_8.toString()));
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
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
        System.out.println(result);
        return "welcome";
    }
    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public String chatUsers(Model model) {
        HttpGet request = null;
        return "tasks";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
