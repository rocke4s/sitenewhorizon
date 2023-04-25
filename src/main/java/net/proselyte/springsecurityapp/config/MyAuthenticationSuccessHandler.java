//package net.proselyte.springsecurityapp.config;
//
//import net.proselyte.springsecurityapp.model.User;
//import net.proselyte.springsecurityapp.service.UserService;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Base64;
//import java.util.Date;
//import java.util.Properties;
//
//@Component
//public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        String userName = authentication.getName();
//        User user = userService.findByUsername(userName);
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        //String userName = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//        System.out.println(user.getUidUser());
//        String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
//        System.out.println(userService.findByUsername(userName).getUidUser());
////      model.addAttribute("user", user);
////        HttpGet request = new HttpGet("http://192.168.1.224/franrit/hs/RitExchange/GetGUID/"+userService.findByUsername(userForm.getUidUser()));
////        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
////        CloseableHttpResponse response = client.execute(request);
////        try {
////            HttpEntity entity = response.getEntity();
////            String result = EntityUtils.toString(entity);
////            EntityUtils.consume(entity);
////            System.out.println(result);
////        } finally {
////            response.close();
////        }
//    }
//    public String[] forBasicAuth() throws IOException{
//        Properties props = new Properties();
//        try (
//                InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
//            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
//        }
//        String username = props.getProperty("username");//Переменные для
//        String password = props.getProperty("password");//basic auth
//        String[] str = new String[]{username,password};
//        return str;
//    }
//}
