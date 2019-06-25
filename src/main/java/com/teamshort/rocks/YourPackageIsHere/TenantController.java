package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TenantController {

    @Autowired
    TenantRepository tenantRepository;





    @GetMapping("/tenant/all")
    public String getAllTenants(Principal principal, Model model){
        String p = principal == null ? "" : principal.getName();
        Iterable<Tenant> tenants = tenantRepository.findAll();
        model.addAttribute("principal", p);
        model.addAttribute("tenants", tenants);
        return "allTenants";
    }

}
