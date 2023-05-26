package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ChangeLogTaskDao;
import net.proselyte.springsecurityapp.model.ChangeLogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeLogTaskServiceImpl implements ChangeLogTaskService{
    @Autowired
    private ChangeLogTaskDao changeLogTaskDao;

    @Override
    public void save(ChangeLogTask changeLogTask) {
        changeLogTask.setNameTask(changeLogTask.getNameTask());
        changeLogTask.setChange(changeLogTask.getChange());
        changeLogTask.setChangetype(changeLogTask.getChangetype());
        changeLogTask.setTime(changeLogTask.getTime());
        changeLogTaskDao.save(changeLogTask);
    }

    public List<ChangeLogTask> findAll() {
        return changeLogTaskDao.findAll();
    }
    public List<ChangeLogTask> findByUidUser(String uidUser) {
        return changeLogTaskDao.findByUidUser(uidUser);
    }
}
