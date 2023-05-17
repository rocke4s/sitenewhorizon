package net.proselyte.springsecurityapp.service;


import net.proselyte.springsecurityapp.dao.RatingTaskDao;
import net.proselyte.springsecurityapp.model.RatingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingTaskServiceImpl implements RatingTaskService{
    @Autowired
    private RatingTaskDao ratingTaskDao;

    @Override
    public void save(RatingTask ratingTask) {
        ratingTask.setRating(ratingTask.getRating());
        ratingTask.setUidDoc(ratingTask.getUidDoc());
        ratingTask.setNameTask(ratingTask.getNameTask());

        ratingTaskDao.save(ratingTask);
    }
   public List<RatingTask> findAll()
   {
       return ratingTaskDao.findAll();
   }
}
