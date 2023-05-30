package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "userschat")
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "numberDoc")
    private String numberDoc;
    @Column(name = "userSender")
    private String userSenders;
    @Column(name = "userRecipient")
    private String userRecipient;
    @Column(name = "message")
    private String message;
    @Column(name = "dateSend")
    private String dateSend;
    @Column(name = "isNewMessage")
    private String isNewMessage;

    public String getIsNewMessage() {
        return isNewMessage;
    }

    public void setIsNewMessage(String isNewMessage) {
        this.isNewMessage = isNewMessage;
    }

    public String getNumberDoc() {
        return numberDoc;
    }

    public void setNumberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getUserSenders() {
        return userSenders;
    }

    public void setUserSenders(String userSenders) {
        this.userSenders = userSenders;
    }

    public String getUserRecipient() {
        return userRecipient;
    }

    public void setUserRecipient(String userRecipient) {
        this.userRecipient = userRecipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }
}
