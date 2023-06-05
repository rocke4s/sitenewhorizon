package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.ChangeLogTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChangeLogTaskDao extends JpaRepository<ChangeLogTask, Long> {
    List<ChangeLogTask> findAll();
    List<ChangeLogTask> findByUidUser(String uidUser);
    List<ChangeLogTask> findByisNewChanges(String isNewChanges);
    @Modifying
    @Query("UPDATE ChangeLogTask SET isNewChanges = :newStatus WHERE numberTask = :numberTask")
    void updateStatusByNumberTask(@Param("newStatus") String newStatus, @Param("numberTask") String numberTask);
}