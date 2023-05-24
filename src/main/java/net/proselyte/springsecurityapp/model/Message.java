package net.proselyte.springsecurityapp.model;

public class Message {
    private String NumberTask;
    private String Name;
    private String message;

    public String getNumberTask() {
        return NumberTask;
    }

    public void setNumberTask(String numberTask) {
        NumberTask = numberTask;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
