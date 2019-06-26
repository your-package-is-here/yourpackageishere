package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;

@Controller
public class TenantController {

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    BuildingRepository buildingRepository;
  
    @GetMapping("/tenant/all")
    public String getAllTenants(Principal principal, Model model) {
        String p = principal == null ? "" : principal.getName();
        if(principal != null){
            Building building = buildingRepository.findByUsername(principal.getName());
            Iterable<Tenant> tenants = tenantRepository.findByBuilding(building);
            model.addAttribute("tenants", tenants);
        }

        model.addAttribute("principal", p);
        return "allTenants";
    }

    @GetMapping("/tenant/{id}")
    public String getTenantPage(@PathVariable String id, Model m) {
        long ID = Long.parseLong(id);
        Tenant tenant = tenantRepository.findById(ID);
        m.addAttribute("tenant", tenant);
        return "tenant";
    }

    @GetMapping("/tenant/add")
    public String getTenantAddPage() {
        return "tenant";
    }

    @PostMapping("/tenantcreate")
    public RedirectView createTenant(Principal p, String firstname, String lastname, String email, String aptnum, String phonenum) throws ParseException {
        Building building = buildingRepository.findByUsername(p.getName());
        Tenant tenant = new Tenant(firstname,lastname,email,aptnum,phonenum, building);
        tenantRepository.save(tenant);

        return new RedirectView("/tenant/all");
    }

    @PutMapping("/tenantedit")
    public RedirectView editTenant(Principal p, String id, String firstname, String lastname, String email, String aptnum, String phonenum) throws ParseException {
        long ID = Long.parseLong(id);
        Tenant tenant = tenantRepository.findById(ID);
        tenant.setFirstname(firstname);
        tenant.setLastname(lastname);
        tenant.setEmail(email);
        tenant.setAptnum(aptnum);
        tenant.setPhonenum(phonenum);
        tenantRepository.save(tenant);

        return new RedirectView("/tenant/all");
    }

    @DeleteMapping("/tenant/{id}/delete")
    public void deleteTenant(Principal p, @PathVariable String id, Model m) {
        long ID = Long.parseLong(id);
        Tenant tenant = tenantRepository.findById(ID);
        tenantRepository.delete(tenant);
        getAllTenants(p, m);
    }

}
