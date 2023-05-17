package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Profile;
import net.proselyte.springsecurityapp.model.RatingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RatingTaskService {
    List<RatingTask> findAll();
    void save(RatingTask ratingTask);
}