//package net.proselyte.springsecurityapp.service;
//
//import net.proselyte.springsecurityapp.dao.TaskListUserDao;
//import net.proselyte.springsecurityapp.model.TaskList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class TaskListServiceImpl implements TaskListService {
//    @Autowired
//    private TaskListUserDao taskListUserDao;
//
//    @Override
//    public void save(TaskList task) {
//        task.setAttachment(task.getAttachment());
//        task.setContentLuvr(task.getContentLuvr());
//        task.setDateCreate(task.getDateCreate());
//        task.setNumberTask(task.getNumberTask());
//        task.setNameTask(task.getNameTask());
//        task.setId(task.getId());
//        task.setTaskPartner(task.getTaskPartner());
//        task.setTypeTask(task.getTypeTask());
//        task.setDateExec(task.getDateExec());
//        task.setDateEnd(task.getDateEnd());
//        task.setDepartment(task.getDepartment());
//        task.setDateUpd(task.getDateUpd());
//        task.setImportance(task.getImportance());
//        task.setStatus(task.getStatus());
//        task.setUidUser(task.getUidUser());
//        task.setLink(task.getLink());
//        task.setUidDoc(task.getUidDoc());
//        task.setLaborCosts(task.getLaborCosts());
//        taskListUserDao.save(task);
//    }
//
//    @Override
//    public List<TaskList> findByisUidUser(String isUidUser, TaskList task) {
//        try{
//            return taskListUserDao.findByisUidUser(isUidUser);
//        }catch (EmptyResultDataAccessException e){
//            save(task);
//            return null;
//        }
//
//    }
//}
