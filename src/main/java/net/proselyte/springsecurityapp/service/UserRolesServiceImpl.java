//package net.proselyte.springsecurityapp.service;
//
//import net.proselyte.springsecurityapp.dao.User_rolesDao;
//import net.proselyte.springsecurityapp.model.User_roles;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class UserRolesServiceImpl implements UserRolesService {
//    @Autowired
//    User_rolesDao user_rolesDao;
//
//    @Transactional
//    public void updateRoleByUid_User(Integer uid_user, Integer role) {
//        user_rolesDao.updateRoleByUid_User(role,uid_user);
//    }
//
//    @Override
//    public User_roles findByUser_id(Integer user_id) {
//        return user_rolesDao.findByUser_id(user_id);
//    }
//}
