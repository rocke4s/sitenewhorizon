package net.proselyte.springsecurityapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class BasicAuthConfig {
    private String ip = "192.168.1.224";//192.168.1.224 || 217.114.183.98
    private String ip2 = "localhost";//localhost || 194.67.111.29
    public String[] forBasicAuth() throws IOException {
        Properties props = new Properties();
        try (
                InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
        }
        String username = props.getProperty("username");//Переменные для
        String password = props.getProperty("password");//basic auth
        String mailName = props.getProperty("mail.username");
        String mailPass = props.getProperty("mail.password");
        String[] str = new String[]{username, password, mailName, mailPass};
        return str;
    }
    public String decodRequest(String text) throws UnsupportedEncodingException {
        byte[] symb = text.getBytes("cp1252");
        text = new String(symb, "cp1251");
        return text;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp2() {
        return ip2;
    }

    public void setIp2(String ip2) {
        this.ip2 = ip2;
    }
}
