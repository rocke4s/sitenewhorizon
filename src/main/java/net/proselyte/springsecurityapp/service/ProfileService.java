package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Profile;
import net.proselyte.springsecurityapp.model.User;

public interface ProfileService {
    Profile findByUidUser(String uidUser);
    void save(Profile profile);
}
