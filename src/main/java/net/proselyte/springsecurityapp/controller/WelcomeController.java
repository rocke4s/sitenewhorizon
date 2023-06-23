package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
@Controller
public class WelcomeController {


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
}
