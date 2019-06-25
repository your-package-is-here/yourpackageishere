package com.teamshort.rocks.YourPackageIsHere;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
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

    @GetMapping("/sendemail")
    public String sendEmail(Principal p, Model m){
        m.addAttribute(p);
        return "/sendemail";
    }

    @PostMapping("/sendemail")
    public RedirectView sendEmailTenant(Principal p, String trackingNumber, String aptnum, String firstname, String lastname, Model m){
        Building manager = buildingRepository.findByUsername(p.getName());
        Boolean isSent = sendEmailHelper(manager.email, "testingpackage1@yandex.com");
        m.addAttribute(isSent);
        m.addAttribute(p);
        return new RedirectView("/sendemail");
    }

    public static Boolean sendEmailHelper(String sender, String receiver){
        Email from = new Email(sender);
        Email to = new Email (receiver);
        String subject = "Your package has arrived";
        Content content = new Content("text/plain", "Your Package is at the office");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return true;
        } catch(
                IOException exception){
            System.out.println(exception);
            return false;
        }
    }

    //to do method for tenant


}
