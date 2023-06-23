package net.proselyte.springsecurityapp.controller;

import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import net.proselyte.springsecurityapp.config.BasicAuthConfig;
import net.proselyte.springsecurityapp.config.Sender;
//import net.proselyte.springsecurityapp.dao.TaskListUserDao;
import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.result_stats.ResultStats;
import net.proselyte.springsecurityapp.service.*;
import net.proselyte.springsecurityapp.validator.UserValidator;

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
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
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
//    @Autowired
//    private UserRolesService userRolesService;
private BasicAuthConfig basicAuthConfig = new BasicAuthConfig();
    //    @Autowired
//    private TaskListService taskListService;

    public UserController() throws IOException {
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserReg());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserReg userForm, BindingResult bindingResult, Model model) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String redName = userForm.getName().replaceAll("[^\\p{IsCyrillic}]+", "");
        redName = redName.substring(0, 1).toUpperCase() + redName.substring(1).toLowerCase();
        String redSurname = userForm.getSurname().replaceAll("[^\\p{IsCyrillic}]+", "");
        redSurname = redSurname.substring(0, 1).toUpperCase() + redSurname.substring(1).toLowerCase();
        String redSecondSurname = userForm.getSecondSurname().replaceAll("[^\\p{IsCyrillic}]+", "");
        redSecondSurname = redSecondSurname.substring(0, 1).toUpperCase() + redSecondSurname.substring(1).toLowerCase();
        userForm.setName(redName);
        userForm.setSurname(redSurname);
        userForm.setSecondSurname(redSecondSurname);
        userForm.setFIO(userForm.getSurname() + "%20" + userForm.getName() + "%20" + userForm.getSecondSurname());
        String test = userForm.getFIO().replaceAll("\\s+", "%20");
        String encoding = Base64.getEncoder().encodeToString((basicAuthConfig.forBasicAuth()[0] + ":" + basicAuthConfig.forBasicAuth()[1]).getBytes());
        HttpGet request = new HttpGet("http://" + basicAuthConfig.getIp() + "/franrit/hs/RitExchange/GetGUID/" + userForm.getUsername()
                + "/" + userForm.getINN() + "/" + userForm.getFIO() + "/" + userForm.getPhone() + "/" + userForm.getUserMail());
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
            User userokForm = new User();
            userokForm.setUidUser(profile.getUidUser());
            userokForm.setUsername(userForm.getUsername());
            userokForm.setUserMail(userForm.getUserMail());
            userokForm.setINN(userForm.getINN());
            userokForm.setPhone(userForm.getPhone());
            userokForm.setPassword(userForm.getPassword());
            userokForm.setConfirmPassword(userForm.getConfirmPassword());
            userokForm.setId(userForm.getId());
            userokForm.setRoles(userForm.getRoles());

            model.addAttribute("profile", profile);
            userService.save(userokForm);//сохранение в бд
            profileService.save(profile);
        }
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout, User user) {
        if (error != null) {
            model.addAttribute("error", "Логин или пароль не совпадают!");
        }

        if (logout != null) {
            model.addAttribute("message", "Выход выполнен успешно!");
        }
        model.addAttribute("user", user);
        return "login";
    }

}