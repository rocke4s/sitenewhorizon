package net.proselyte.springsecurityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "RatingTask")
public class RatingTask {
    @Id
    @Column(name = "uidDoc")
    private String uidDoc;
    @Column(name = "rating")
    private String rating;
    @Column(name = "nameTask")
    private String nameTask;

    public String getUidDoc() {
        return uidDoc;
    }

    public void setUidDoc(String uidDoc) {
        this.uidDoc = uidDoc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
