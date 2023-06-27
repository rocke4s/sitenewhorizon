package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class WelcomeController {
    private int countUserOnline = 0;
    private List<String> users = new ArrayList<>();

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(@ModelAttribute("user") User user, Model model) throws IOException {
        System.out.println(user.getUsername());
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" + forBasicAuth()[1]).getBytes());
//        HttpGet request = new HttpGet("http://" + ip + "/franrit/hs/RitExchange/getRole/" + user.getUidUser());
//        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
//        CloseableHttpResponse response = null;
//        try {
//            if(userRolesService.findByUser_id(user.getId()).getRole_id()==1L)
//            {
//                response = client.execute(request);//выполняем запрос
//                HttpEntity entity = response.getEntity();//получаем ответ от АПИ
//                String result = EntityUtils.toString(entity);//засовываем ответ в строку
//                EntityUtils.consume(entity);
//                Gson g = new Gson();
//                String str = result;
//                if(admin)
//                {
//                    userRolesService.updateRoleByUid_User("3",user.getId().toString());
//                }
//                if(superuser)
//                {
//                    userRolesService.updateRoleByUid_User("2",user.getId().toString());
//                }
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
        return "welcome";
    }

    @RequestMapping(value = {"/getUsersOnline"}, method = RequestMethod.GET)
    @ResponseBody
    public String getUsersOnline() {
        int count = getCountOfUsersOnline();
        return Integer.toString(count);
    }

    @RequestMapping(value = {"/addUserToOnlineList"}, method = RequestMethod.GET)
    public void addUserToOnlineList(@RequestHeader("user") String user) {
        if (users.size() == 0) {
            users.add(user);
            countUserOnline++;

            // Создаем новый таймер и запускаем его через 2 минуты
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    users.remove(user);
                    countUserOnline--;
                }
            }, 120000);
        } else {
            int countCorr=0;
            for (int x = 0; x < users.size(); x++) {
                if (users.get(x).equals(user)) {
                    countCorr++;
                }
            }
            if(countCorr==0)
            {
                users.add(user);
                countUserOnline++;

                // Создаем новый таймер и запускаем его через 2 минуты
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        users.remove(user);
                        countUserOnline--;
                    }
                }, 120000);
            }
        }
    }

    private int getCountOfUsersOnline() {
        return countUserOnline;
    }
}
