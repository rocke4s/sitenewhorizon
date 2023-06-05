package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ChangeLogTaskDao;
import net.proselyte.springsecurityapp.model.ChangeLogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChangeLogTaskServiceImpl implements ChangeLogTaskService{
    @Autowired
    private ChangeLogTaskDao changeLogTaskDao;

    @Override
    public void save(ChangeLogTask changeLogTask) {
        changeLogTask.setUidUser(changeLogTask.getUidUser());
        changeLogTask.setChangetype(changeLogTask.getChangetype());
        changeLogTask.setNameTask(changeLogTask.getNameTask());
        changeLogTask.setChange(changeLogTask.getChange());
        changeLogTask.setTime(changeLogTask.getTime());
        changeLogTask.setNumberTask(changeLogTask.getNumberTask());
        changeLogTask.setIsNewChanges(changeLogTask.getIsNewChanges());
        changeLogTaskDao.save(changeLogTask);
    }

    @Override
    public List<ChangeLogTask> findByisNewChanges(String isNewChanges)
    {
        return changeLogTaskDao.findByisNewChanges(isNewChanges);
    }
    @Transactional
    public void updateStatusByNumberTask(String newStatus, String numberTask) {
        changeLogTaskDao.updateStatusByNumberTask(newStatus, numberTask);
    }

    public List<ChangeLogTask> findAll() {
        return changeLogTaskDao.findAll();
    }
    public List<ChangeLogTask> findByUidUser(String uidUser) {
        return changeLogTaskDao.findByUidUser(uidUser);
    }
}
