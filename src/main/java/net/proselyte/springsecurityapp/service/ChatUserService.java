package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.ChatUser;

import java.util.List;

public interface ChatUserService {
    List<ChatUser> findByisNewMessage(String isNewMessage);
    void save(ChatUser chatUser);
}
