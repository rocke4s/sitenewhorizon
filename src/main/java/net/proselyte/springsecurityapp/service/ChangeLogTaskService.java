package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.ChangeLogTask;

import java.util.List;

public interface ChangeLogTaskService {
    void save(ChangeLogTask changeLogTask);
    List<ChangeLogTask> findAll();
    List<ChangeLogTask> findByUidUser(String uidUser);
    List<ChangeLogTask> findByisNewChanges(String isNewChanges);
    void updateStatusByNumberTask(String newStatus, String numberTask);


}
