package net.proselyte.springsecurityapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;


@Generated("jsonschema2pojo")
public class Task {
    @SerializedName("statusTask")
    @Expose
    private String statusTask;

    @SerializedName("Tasks")
    @Expose
    private List<Tasks> tasks;

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

}

