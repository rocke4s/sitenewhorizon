package net.proselyte.springsecurityapp.model;

public class Statuska {
    private String oldStatus;
    private String newStatus;
    private String nameTask;
    private String numberTask;
    private String userName;

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(String numberTask) {
        this.numberTask = numberTask;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Statuska{" +
                "oldStatus='" + oldStatus + '\'' +
                ", newStatus='" + newStatus + '\'' +
                ", nameTask='" + nameTask + '\'' +
                ", numberTask='" + numberTask + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
