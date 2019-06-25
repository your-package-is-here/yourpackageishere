package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TenantController {

    @Autowired
    TenantRepository tenantRepository;

    @GetMapping("/tenant/{id}")
    public String getTenantPage(@PathVariable String id, Model m) {
        long ID = Long.parseLong(id);
        Tenant tenant = tenantRepository.findById(ID);
        m.addAttribute("tenant", tenant);
        return "tenant";
    }

}
