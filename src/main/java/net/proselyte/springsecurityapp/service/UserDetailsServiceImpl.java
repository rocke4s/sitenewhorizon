package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //String userName = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        System.out.println(user.getUidUser());
        try {
            String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(userDao.findByUsername(username).getUidUser());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
    public User getProfile(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        System.out.println(user.getUidUser());


        return user;
    }
    public String[] forBasicAuth() throws IOException {
        Properties props = new Properties();
        try (
                InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
        }
        String username = props.getProperty("username");//Переменные для
        String password = props.getProperty("password");//basic auth
        String[] str = new String[]{username,password};
        return str;
    }
}
