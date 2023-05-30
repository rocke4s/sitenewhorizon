package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.ChangeLogTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeLogTaskDao extends JpaRepository<ChangeLogTask, Long> {
    List<ChangeLogTask> findAll();
    List<ChangeLogTask> findByUidUser(String uidUser);
    List<ChangeLogTask> findByisNewChanges(String isNewChanges);
}