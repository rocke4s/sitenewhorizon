package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ChatUserDao;
import net.proselyte.springsecurityapp.model.ChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatUserServiceImpl  implements ChatUserService{
    @Autowired
    private ChatUserDao chatUserDao;

    @Override
    public void save(ChatUser chatUser) {
        chatUser.setNumberTask(chatUser.getNumberTask());
        chatUser.setUserSenders(chatUser.getUserSenders());
        chatUser.setUserRecipient(chatUser.getUserRecipient());
        chatUser.setMessage(chatUser.getMessage());
        chatUser.setDateSend(chatUser.getDateSend());
        chatUser.setIsNewMessage(chatUser.getIsNewMessage());
        chatUserDao.save(chatUser);
    }
    @Override
    public List<ChatUser> findByisNewMessage(String isNewMessage)
    {
        return chatUserDao.findByisNewMessage(isNewMessage);
    }
}
