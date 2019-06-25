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
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BuildingController {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    TenantRepository tenantRepository;


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
    public String getHomePage(Principal principal, Model model) {
        String p = principal == null ? "" : principal.getName();
        model.addAttribute("principal", p);
        return "home";
    }

    @GetMapping("/aboutus")
    public String getSignUpPage(Principal principal, Model model) {
        String p = principal == null ? "" : principal.getName();
        model.addAttribute("principal", p);
        return "aboutus";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal principal, Model model) {
        String p = principal == null ? "" : principal.getName();
        model.addAttribute("principal", p);
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Principal principal, Model model) {
        String p = principal == null ? "" : principal.getName();
        model.addAttribute("principal", p);
        return "register";
    }

    @GetMapping("/sendemail")
    public String sendEmail(Principal p, Model m){
        m.addAttribute(p);
        return "/sendemail";
    }

    @PostMapping("/sendemail")
    public RedirectView sendEmailTenant(Principal p, Model m, String trackingnumber, String aptnum, String firstname, String lastname){
        //Get the manager object
        Building manager = buildingRepository.findByUsername(p.getName());

        //Do logic to find the appropriate user(s) to send the email too
        List<Tenant> tenants = getTenantHelper(firstname, lastname, aptnum);
        //This will send the email
        Boolean isSent = false;
        // for loop to send email to all the tenants in the apartment if first name and last name does not match.
        for(Tenant tenant: tenants){
            isSent = sendEmailHelper(manager.email, tenant.email);
        }
        m.addAttribute(isSent);
        m.addAttribute("isTenants", !tenants.isEmpty());
        m.addAttribute(p);
        return new RedirectView("/sendemail");
    }

    // This method sends the email to the appropriate user using sendgrid api
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

    // This funciton returns a List of all possible and appropriate tenants.
    public List<Tenant> getTenantHelper(String firstname, String lastname, String aptnum){
        // Gets a list of tenants ignoring case on first and last name
        List<Tenant> tenants = tenantRepository.findByFirstnameIgnoreCaseContainingAndLastnameIgnoreCaseContaining(firstname, lastname);
        // If there are matches on the first and last name
        if(!tenants.isEmpty()){
            // Checks to see if the list contains the correct apt number
            if(containsAptnum(tenants, aptnum)){
                // Returns the tenants whose first and last name and apt number match
                tenants = tenants
                        .stream()
                        .filter(tenant -> tenant.getAptnum().toLowerCase().equals(aptnum.toLowerCase()))
                        .collect(Collectors.toList());
                return tenants;
            }else {
                // Returns all tenants via first and last name is apt is not a match
                return tenants;
            }
        }else{
            // If we found not matches based on name go solely on apt number
            tenants = tenantRepository.findByAptnumIgnoreCaseContaining(aptnum);
        }
        // Return whatever tenants we have acquired.
        return tenants;
    }

    // This function filters and returns true or false if there is a tenant with a matching apt number
    public boolean containsAptnum(final List<Tenant> tenants, String aptnum){
        return tenants.stream().filter(tenant -> tenant.getAptnum().toLowerCase().equals(aptnum.toLowerCase())).findFirst().isPresent();
    }
}
