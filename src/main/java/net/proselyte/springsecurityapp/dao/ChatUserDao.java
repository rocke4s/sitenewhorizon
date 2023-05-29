package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ChatUserDao extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findByisNewMessage(String isNewMessage);
}
