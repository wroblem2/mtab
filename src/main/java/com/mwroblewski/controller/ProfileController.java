package com.mwroblewski.controller;

import com.mwroblewski.entity.Profile;
import com.mwroblewski.entity.User;
import com.mwroblewski.repository.ProfileDAO;
import com.mwroblewski.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    ProfileDAO profileDAO;

    @GetMapping("/all")
    public List<Profile> getAllProfile() {

        return (List<Profile>) profileDAO.findAll();
    }

    @GetMapping("/user")
    public Profile getUserProfile(@AuthenticationPrincipal Principal principal){

        User user = userDAO.findByUsername(principal.getName());

        return user.getProfile();
    }

}
