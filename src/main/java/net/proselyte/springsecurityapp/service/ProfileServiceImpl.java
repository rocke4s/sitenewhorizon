package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ProfileDao;
import net.proselyte.springsecurityapp.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileDao profileDao;

    @Override
    public void save(Profile profile) {
        profile.setName(profile.getName());
        profile.setOrgName(profile.getOrgName());
        profile.setUidUser(profile.getUidUser());
        profile.setUidOrg(profile.getUidOrg());
        profile.setDebt(profile.getDebt());
        profileDao.save(profile);
    }
    @Override
    public Profile findByUidUser(String uidUser) {
        return profileDao.findByUidUser(uidUser);
    }
}
