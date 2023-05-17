package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.RatingTask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingTaskDao extends JpaRepository<RatingTask, String> {
    List<RatingTask> findAll();
}
