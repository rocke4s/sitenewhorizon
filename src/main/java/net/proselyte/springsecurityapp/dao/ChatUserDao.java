package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUserDao extends JpaRepository<ChatUser, Long> {

    List<ChatUser> findByisNewMessage(String isNewMessage);
    @Modifying
    @Query("UPDATE ChatUser SET isNewMessage = :newStatus WHERE numberDoc = :numberTask")
    void updateChatByNumberDoc(@Param("newStatus") String newStatus, @Param("numberTask") String numberTask);
}
