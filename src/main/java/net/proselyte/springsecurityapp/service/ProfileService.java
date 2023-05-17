package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Profile;

public interface ProfileService {
    Profile findByUidUser(String uidUser);
    void save(Profile profile);
}
