package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuildingController {

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
