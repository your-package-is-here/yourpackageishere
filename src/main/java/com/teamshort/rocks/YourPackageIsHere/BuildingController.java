package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class BuildingController {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    BuildingRepository buildingRepository;


    @PostMapping("/buildingcreate")
    public RedirectView createUser(String username, String name, String streetaddress, String city, String state, String zip, String email, String password) throws ParseException {
        String hashedpwd = bCryptPasswordEncoder.encode(password);
        Building newBuilding = new Building(username,name, streetaddress, city, state, zip, email, hashedpwd);
        buildingRepository.save(newBuilding);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newBuilding, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/");
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/aboutus")
    public String getSignUpPage() {
        return "aboutus";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }



}
