package net.proselyte.springsecurityapp.model;

public class ChangeStatus {
    private String causeChangeStatus;
    private String status;

    public String getCauseChangeStatus() {
        return causeChangeStatus;
    }

    public void setCauseChangeStatus(String causeChangeStatus) {
        this.causeChangeStatus = causeChangeStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
