package net.proselyte.springsecurityapp.model;

public class Until {
    private String NumberTask;
    private String NameTask;
    private String Until;
    private String ChangeType;
    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getChangeType() {
        return ChangeType;
    }

    public void setChangeType(String changeType) {
        ChangeType = changeType;
    }

    public String getNumberTask() {
        return NumberTask;
    }

    public void setNumberTask(String numberTask) {
        NumberTask = numberTask;
    }

    public String getNameTask() {
        return NameTask;
    }

    public void setNameTask(String nameTask) {
        NameTask = nameTask;
    }

    public String getUntil() {
        return Until;
    }

    public void setUntil(String until) {
        Until = until;
    }
}
