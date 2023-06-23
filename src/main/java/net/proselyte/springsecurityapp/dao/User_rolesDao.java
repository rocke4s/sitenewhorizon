//package net.proselyte.springsecurityapp.dao;
//
//import net.proselyte.springsecurityapp.model.User_roles;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//public interface User_rolesDao extends JpaRepository<User_roles, Long> {
//    @Modifying
//    @Query("UPDATE user_roles ur SET ur.role_id = ?1 WHERE ur.user_id = ?2")
//    void updateRoleByUid_User(Integer role_id, Integer user_id);
//    User_roles findByUser_id(Integer user_id);
//}
