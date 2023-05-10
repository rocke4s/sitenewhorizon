package net.proselyte.springsecurityapp.config;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Sender {

    private static final String SMTP_HOST_NAME = "smtp.mail.ru";
    private static final String SMTP_AUTH_USER = "info.max98@mail.ru";
    private static final String SMTP_AUTH_PASSWORD = "Q3S9WHts2VnjfrXUc4Qv";

    public void send(String recipient, String subject, String message) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST_NAME);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PASSWORD);
            }
        });
        Message messagse = new MimeMessage(session);
        messagse.setFrom(new InternetAddress("info.max98@mail.ru"));
        messagse.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        messagse.setSubject(subject);

        messagse.setContent(message, "text/plain; charset=UTF-8");
        messagse.setHeader("Content-Transfer-Encoding", "quoted-printable");

            Transport.send(messagse);
    }
}
