package net.proselyte.springsecurityapp.controller;

import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import net.proselyte.springsecurityapp.config.MyWebSocketClient;
import net.proselyte.springsecurityapp.config.Sender;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.ProfileService;
import net.proselyte.springsecurityapp.service.RatingTaskService;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
    private RatingTaskService ratingTaskService;
    @Autowired
    private UserValidator userValidator;
    private String ip="217.114.183.98";//192.168.1.224 || 217.114.183.98
    private String ip2="194.67.111.29";//localhost || 194.67.111.292

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
    public void doGets(HttpServletRequest request) {
        try {
            // Получаем данные из GET запроса
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");
            System.out.println("asdfqwe");




            String NameTask = request.getParameter("NameTask");
            String NumberTask = request.getParameter("NumberTask");
            String OldStatus = request.getParameter("OldStatus");
            String NewStatus = request.getParameter("NewStatus");
            String UserName = request.getParameter("UserName");

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
        } catch (Exception e) {
            System.out.println(e);
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
        userForm.setFIO(userForm.getSurname()+"%20"+userForm.getName()+"%20"+userForm.getSecondSurname());
        String test = userForm.getFIO().replaceAll("\\s+","%20");
        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
        HttpGet request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetGUID/"+userForm.getUsername()
                +"/"+userForm.getINN()+"/"+userForm.getFIO()+"/"+userForm.getPhone()+"/"+userForm.getUserMail());
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
            User userokForm=new User();
            userokForm.setUidUser(profile.getUidUser());
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
    public String welcome(@ModelAttribute("user")User user, Model model) throws IOException {
        System.out.println(user.getUsername());
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
            str = str.replaceAll("\"Отдел\"", "TaskDepartment");
            task = g.fromJson(str, Task.class);

            Collections.sort(task.getTasks(), Comparator.comparing(Tasks::getTaskNumber).reversed());
        } finally {
            response.close();
        }
        ModelAndView modelAndView = new ModelAndView("tasks");
        modelAndView.addObject("Tasks", task);
        modelAndView.addObject("changeStatus",changeStatus);
        modelAndView.addObject("chat",chat);
        return modelAndView;
    }
    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public String showRatings(Model model)
    {
        List<RatingTask> listRatingTask = ratingTaskService.findAll();
        System.out.println("ky");
        model.addAttribute("rating",listRatingTask);
        return "ratings";
    }
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public String ratingWork(String ratingg,String NameTasker,String uidDoc_8) {
        System.out.println("Пользователь оценил работу на: "+ratingg);
        RatingTask ratingTask = new RatingTask();
        ratingTask.setRating(ratingg);
        ratingTask.setUidDoc(uidDoc_8);
        ratingTask.setNameTask(NameTasker);
        ratingTaskService.save(ratingTask);
        return "redirect:/tasks";
    }
    @RequestMapping(value = "/change_status", method = RequestMethod.GET)
    public String changeStatus(@ModelAttribute("changeStatus")ChangeStatus changeStatus,Authentication authentication,
                               String uidDoc_8,String uidDoc_5,String uidDoc_0) throws IOException, MessagingException {
        HttpGet request = null;
        String doc="";
        String stateDoc="";
        if(uidDoc_5!=null && !uidDoc_5.isEmpty())//передаем выбранное состояние заявки - "на доработку"
        {
            changeStatus.setCauseChangeStatus(changeStatus.getCauseChangeStatus().replaceAll("\\s+","%20"));
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
            request = new HttpGet("http://"+ip+"/franrit/hs/RitExchange/GetTestResult/"+uidDoc_0+"/3");
            doc=uidDoc_0;
            stateDoc="Отменено";
        }
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


//        Sender sender1 = new Sender();
//        sender1.send("rocke4max@gmail.com", "Изменение статуса заявки", "Статус заявки №"+doc+" изменился на "
//                +stateDoc);

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
        String fileName= "";
        if(!newTask.getFile().isEmpty()) {
            fileName = StringUtils.cleanPath(newTask.getFile().getOriginalFilename());
            try {
                File directory = new File("data/"+newTask.getNameTask());
                directory.mkdir();
                Path path = Paths.get("data/"+newTask.getNameTask()+"/" + fileName);
                Files.copy(newTask.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
         newTask.setNameTask(newTask.getNameTask().replaceAll("\\s+","%20"));
         newTask.setTaskContent(newTask.getTaskContent().replaceAll("\\s+","%20"));
         fileName = fileName.replaceAll("\\s+","%20");
        String q  = "http://"+ip2+"/data/"+newTask.getNameTask()+"/" + fileName;
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

    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public void doChat(HttpServletRequest request) {
        try {
            // Получаем данные из GET запроса
            String uidDoc = request.getParameter("uidDoc");
            String Name = request.getParameter("Name");
            String message = request.getParameter("message");


            // Отправляем полученное сообщение через WebSocket

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://"+ip2+":80/chat";
            MyWebSocketClient endpoint = new MyWebSocketClient();
            Session session = container.connectToServer(endpoint, new URI(uri));
            endpoint.sendMessage("{'uidDoc'='"+uidDoc+"','Name'='"+Name+"','message'='"+message+"'}");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
