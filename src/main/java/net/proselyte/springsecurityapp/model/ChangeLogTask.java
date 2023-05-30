package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "ChangeLogTask")
public class ChangeLogTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="changetype")
    private String changetype;
    @Column(name="nameTask")
    private String nameTask;
    @Column(name="change")
    private String change;
    @Column(name="time")
    private String time;
    @Column(name="uiduser")
    private String uidUser;
    @Column(name="isNewChanges")
    private String isNewChanges;

    public String getIsNewChanges() {
        return isNewChanges;
    }

    public void setIsNewChanges(String isNewChanges) {
        this.isNewChanges = isNewChanges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
