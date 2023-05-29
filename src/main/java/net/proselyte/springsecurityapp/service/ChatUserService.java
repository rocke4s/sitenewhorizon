package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Chat;
import net.proselyte.springsecurityapp.model.ChatUser;
import net.proselyte.springsecurityapp.model.Profile;

import java.util.Date;
import java.util.List;

public interface ChatUserService {
    List<ChatUser> findByisNewMessage(String isNewMessage);
    void save(ChatUser chatUser);
}
