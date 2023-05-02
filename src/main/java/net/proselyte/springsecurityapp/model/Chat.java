package net.proselyte.springsecurityapp.model;

public class Chat {
    private String message;
    private String uidDoc;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUidDoc() {
        return uidDoc;
    }

    public void setUidDoc(String uidDoc) {
        this.uidDoc = uidDoc;
    }
}
