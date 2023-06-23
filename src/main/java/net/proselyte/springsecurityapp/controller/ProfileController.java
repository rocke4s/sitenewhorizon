package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Profile;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.ProfileService;
import net.proselyte.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profile(Model model, Authentication authentication) {
        System.out.println(authentication.getName());
        User user = userService.findByUsername(authentication.getName());
        Profile prof = profileService.findByUidUser(user.getUidUser());
        model.addAttribute("Profile", prof);
        return "profile";
    }
}
