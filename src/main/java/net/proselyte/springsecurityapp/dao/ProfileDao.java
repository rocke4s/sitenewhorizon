package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile, Long> {
    Profile findByUidUser(String uidUser);
}
